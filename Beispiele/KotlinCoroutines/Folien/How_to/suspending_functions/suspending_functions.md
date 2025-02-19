Wenn du eine **normale Funktion** (keine `suspend`-Funktion) innerhalb eines `runBlocking`-Blocks ausführst, passiert Folgendes:

- Die **normale Funktion wird ganz normal synchron ausgeführt**.
- **`runBlocking` verhält sich wie eine normale Funktion**, die den aktuellen **Thread blockiert**, bis der gesamte Code innerhalb abgeschlossen ist.
- **Es gibt keine Coroutines oder asynchrone Verarbeitung**, weil die Funktion **nicht ausgesetzt (`suspend`)** werden kann.

---

## **🔹 Beispiel: Normale Funktion in `runBlocking`**
```kotlin
import kotlinx.coroutines.*

fun normaleFunktion() {
    println("Start normale Funktion")
    Thread.sleep(1000) // Blockiert den aktuellen Thread für 1 Sekunde
    println("Ende normale Funktion")
}

fun main() = runBlocking {
    println("Start runBlocking")
    
    normaleFunktion() // Normale Funktion aufrufen

    println("Ende runBlocking")
}
```

### **🔹 Output (Synchron)**
```
Start runBlocking
Start normale Funktion
(1 Sekunde Pause)
Ende normale Funktion
Ende runBlocking
```

### **🔹 Was passiert hier genau?**
1. `runBlocking` startet eine **blockierende Coroutine** im **Hauptthread**.
2. `normaleFunktion()` wird **direkt aufgerufen**, weil sie keine `suspend`-Funktion ist.
3. **`Thread.sleep(1000)` blockiert den Thread** für 1 Sekunde.
4. Nach Abschluss läuft `runBlocking` weiter und beendet sich.

🚨 **Kein Vorteil durch Coroutines!**  
Da die Funktion **nicht `suspend` ist**, wird **der Thread komplett blockiert** – genau wie in normalem Java-Code.

---

## **🔹 Was passiert, wenn ich stattdessen `suspend` nutze?**
```kotlin
import kotlinx.coroutines.*

suspend fun suspendingFunktion() {
    println("Start suspending Funktion")
    delay(1000) // Wartet 1 Sekunde, aber gibt den Thread frei
    println("Ende suspending Funktion")
}

fun main() = runBlocking {
    println("Start runBlocking")
    
    suspendingFunktion() // Suspend-Funktion aufrufen

    println("Ende runBlocking")
}
```

### **🔹 Output (Nicht blockierend)**
```
Start runBlocking
Start suspending Funktion
(1 Sekunde Pause, aber Thread kann andere Coroutines ausführen!)
Ende suspending Funktion
Ende runBlocking
```

### **🔹 Was passiert hier?**
1. `runBlocking` startet eine **blockierende Coroutine**.
2. **`suspendingFunktion()` wird gestartet und gibt mit `delay(1000)` den Thread sofort frei.**
3. Andere Coroutines könnten währenddessen laufen (aber `runBlocking` blockiert das Hauptprogramm!).
4. Nach 1 Sekunde setzt sich die Funktion fort und beendet sich.

---

## **🔹 Fazit: Normale Funktion vs. `suspend`-Funktion in `runBlocking`**
| **Funktionstyp** | **Verhalten in `runBlocking`** | **Thread-Blocking?** |
|--------------|------------------|------------------|
| **Normale Funktion** (`fun`) | Wird synchron ausgeführt, **keine Vorteile durch Coroutines** | ✅ **Ja, blockiert Thread** |
| **Suspend-Funktion** (`suspend fun`) | Kann pausieren (`delay()`) und **Thread für andere Aufgaben freigeben** | ❌ **Nein, nicht blockierend** |

---

## **🔥 TL;DR**
- **Normale Funktionen in `runBlocking` blockieren den Thread vollständig** → Kein Vorteil durch Coroutines!
- **Suspend-Funktionen (`suspend fun`) können sich aussetzen (`delay()`) und ermöglichen nicht-blockierende Nebenläufigkeit**.
- **Nutze immer `suspend fun`, wenn du Coroutines effizient nutzen möchtest!** 🚀



### **Werden mehrere `suspend`-Funktionen in `runBlocking` parallel ausgeführt?**
Nein, **wenn du mehrere `suspend`-Funktionen direkt in `runBlocking` aufrufst, werden sie sequentiell (nacheinander) ausgeführt** – nicht parallel!

### **🚨 Wichtige Regel:**
- Ein einfacher `suspend`-Funktionsaufruf **blockiert die Coroutine bis zum Abschluss der Funktion**.
- **Parallelität** erreichst du nur mit **`launch {}` oder `async {}`**.

---

## **1️⃣ Beispiel: Sequentielle Abarbeitung von `suspend`-Funktionen**
```kotlin
import kotlinx.coroutines.*

suspend fun task1() {
    delay(1000) // Wartet 1 Sekunde
    println("Task 1 fertig")
}

suspend fun task2() {
    delay(1000) // Wartet 1 Sekunde
    println("Task 2 fertig")
}

fun main() = runBlocking {
    task1()  // Wird komplett ausgeführt
    task2()  // Startet erst, wenn `task1()` fertig ist
}
```

### **🔹 Output (Sequentielle Verarbeitung)**
```
(1 Sekunde Pause)
Task 1 fertig
(1 Sekunde Pause)
Task 2 fertig
```
➡️ **Warum?** Weil `runBlocking` wartet, bis `task1()` fertig ist, bevor `task2()` startet.

✅ **Das Verhalten entspricht einem normalen synchronen Funktionsaufruf.**

---

## **2️⃣ Lösung: `launch` für parallele Verarbeitung (kein Rückgabewert)**
**Wenn du `suspend`-Funktionen parallel ausführen willst, nutze `launch {}`!**

```kotlin
import kotlinx.coroutines.*

suspend fun task1() {
    delay(1000)
    println("Task 1 fertig")
}

suspend fun task2() {
    delay(1000)
    println("Task 2 fertig")
}

fun main() = runBlocking {
    launch { task1() } // Startet task1 parallel
    launch { task2() } // Startet task2 parallel
}
```

### **🔹 Output (Parallele Verarbeitung mit `launch`)**
```
(1 Sekunde Pause)
Task 1 fertig
Task 2 fertig
```
✅ **Jetzt laufen beide `suspend`-Funktionen parallel!**

---

## **3️⃣ `async` für parallele Verarbeitung mit Rückgabewerten**
**Wenn du `suspend`-Funktionen parallel starten willst und einen Wert zurückbekommen möchtest, nutze `async {}` mit `.await()`.**

```kotlin
import kotlinx.coroutines.*

suspend fun fetchData1(): String {
    delay(1000)
    return "Ergebnis 1"
}

suspend fun fetchData2(): String {
    delay(1000)
    return "Ergebnis 2"
}

fun main() = runBlocking {
    val result1 = async { fetchData1() }
    val result2 = async { fetchData2() }

    println("Result 1: ${result1.await()}")
    println("Result 2: ${result2.await()}")
}
```

### **🔹 Output (Parallele Verarbeitung mit `async`)**
```
(1 Sekunde Pause)
Result 1: Ergebnis 1
Result 2: Ergebnis 2
```
✅ **Hier laufen beide `fetchData()`-Funktionen gleichzeitig und wir warten erst am Ende mit `.await()`.**

---

## **4️⃣ Wann nutze ich `launch` und wann `async`?**
| **Funktion**  | **Wann verwenden?** | **Rückgabewert?** |
|--------------|----------------|----------------------|
| **`launch`** | Wenn du **eine Nebenläufige Aufgabe starten willst, die nichts zurückgibt** (z. B. Logging, Fire-and-Forget). | ❌ Nein |
| **`async`** | Wenn du **einen Wert von einer parallelen Aufgabe zurückbekommen möchtest**. | ✅ Ja, über `.await()` |

---

## **5️⃣ Fazit: Wie mache ich Code parallel?**
| **Code**  | **Parallel?** | **Wartet auf vorherige Aufgabe?** |
|--------------|----------------|----------------------|
| `task1(); task2();` | ❌ Nein | ✅ Ja |
| `launch { task1() }; launch { task2() };` | ✅ Ja | ❌ Nein |
| `async { task1() }.await(); async { task2() }.await();` | ❌ Nein | ✅ Ja (da `.await()` direkt aufgerufen wird) |
| `val a = async { task1() }; val b = async { task2() }; a.await(); b.await();` | ✅ Ja | ❌ Nein (da `.await()` erst nach Start beider Tasks erfolgt) |

🚀 **Fazit:**
- **Ohne `launch` oder `async` → Alles läuft sequentiell.**
- **Mit `launch` → Parallel, aber ohne Rückgabewert.**
- **Mit `async {}` + `.await()` → Parallel mit Rückgabewert.**

**Jetzt weißt du, wie man Coroutines wirklich parallel macht!** 🚀🔥
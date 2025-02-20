## **📌 `withContext` – Kontextwechsel für Coroutines**

`withContext {}` ist ein **Coroutine-Builder in Kotlin**, mit dem du innerhalb einer `suspend`-Funktion den **Coroutine-Dispatcher oder den Kontext wechseln kannst**.

### **🚀 Wichtige Eigenschaften von `withContext`:**
✅ **Blockiert NICHT den Thread**, sondern nur die Coroutine (also suspendiert sie).  
✅ **Wechselt temporär den `Dispatcher`** für bestimmte Codeblöcke.  
✅ **Gibt einen Wert zurück**, da es ein `suspend`-Funktion ist.  
✅ **Läuft innerhalb einer bestehenden Coroutine** – startet KEINE neue Coroutine.

---

## **📌 1. Beispiel: `withContext` für Dispatcher-Wechsel**
```kotlin
import kotlinx.coroutines.*

fun main() = runBlocking {
    println("Start auf Thread: ${Thread.currentThread().name}")

    val result = withContext(Dispatchers.IO) { 
        println("Wechsle auf IO-Thread: ${Thread.currentThread().name}")
        delay(1000)
        "Ergebnis geladen"
    }

    println("Zurück im Main-Thread: ${Thread.currentThread().name}")
    println("Ergebnis: $result")
}
```
### **🔍 Ausgabe**
```
Start auf Thread: main  
Wechsle auf IO-Thread: DefaultDispatcher-worker-1  
Zurück im Main-Thread: main  
Ergebnis: Ergebnis geladen
```
👉 **Erklärung:**
1. Die `runBlocking {}`-Coroutine startet auf `main`.
2. `withContext(Dispatchers.IO) {}` wechselt auf den `IO`-Thread für die Datenverarbeitung.
3. Nach `withContext` kehrt die Coroutine zurück zum ursprünglichen Thread (`main`).

---

## **📌 2. Unterschied zwischen `withContext` und `launch`**
| **Feature**         | **`withContext`** (Suspend-Funktion) | **`launch`** (Coroutine-Builder) |
|--------------------|--------------------------------|--------------------------------|
| **Startet eine neue Coroutine?** | ❌ Nein | ✅ Ja |
| **Gibt ein Ergebnis zurück?** | ✅ Ja (`return`) | ❌ Nein (nur `Job`) |
| **Blockiert die Coroutine?** | ✅ Ja (aber nicht den Thread) | ❌ Nein (läuft parallel) |
| **Thread-Wechsel?** | ✅ Ja | ✅ Ja |

### **📌 Beispiel: `withContext` vs. `launch`**
```kotlin
import kotlinx.coroutines.*

fun main() = runBlocking {
    // Nutzt withContext (Wartet auf das Ergebnis)
    val result = withContext(Dispatchers.IO) { 
        delay(1000)
        "Daten geladen"
    }
    println("Ergebnis: $result") // Wird erst nach dem Kontextwechsel ausgeführt

    // Nutzt launch (Läuft parallel, aber gibt nichts zurück)
    launch(Dispatchers.IO) { 
        delay(1000)
        println("Hintergrundaufgabe abgeschlossen")
    }

    println("Haupt-Thread läuft weiter!")
}
```
### **🔍 Verhalten**
- `withContext` **wartet auf das Ergebnis** und gibt es zurück.
- `launch` startet **eine parallele Coroutine**, aber **wartet nicht** darauf.

---

## **📌 3. `withContext` für CPU-intensive Aufgaben (`Dispatchers.Default`)**
```kotlin
import kotlinx.coroutines.*

fun main() = runBlocking {
    val result = withContext(Dispatchers.Default) { 
        (1..1_000_000).sum() // Intensive Berechnung
    }
    println("Summe berechnet: $result")
}
```
👉 **Nutze `Dispatchers.Default` für Berechnungen**, damit dein UI-Thread nicht blockiert wird.

---

## **📌 4. Fehlerhandling mit `withContext`**
```kotlin
import kotlinx.coroutines.*

fun main() = runBlocking {
    try {
        withContext(Dispatchers.IO) {
            throw RuntimeException("Ein Fehler ist aufgetreten!")
        }
    } catch (e: Exception) {
        println("Fehler gefangen: ${e.message}")
    }
}
```
👉 **Fehler innerhalb von `withContext` können direkt mit `try-catch` behandelt werden**.

---

## **📌 5. `withContext` für Datenbank-Operationen**
**Typische Verwendung von `withContext(Dispatchers.IO)` in einer Repository-Klasse:**
```kotlin
class UserRepository {
    suspend fun getUser(): User {
        return withContext(Dispatchers.IO) {
            database.getUser() // Datenbankabfrage (blockierend)
        }
    }
}
```
👉 **Datenbankabfragen laufen auf `Dispatchers.IO`, um den Haupt-Thread nicht zu blockieren!**

---

## **🚀 Fazit: Wann `withContext` verwenden?**
✅ **Wenn du in einer bestehenden Coroutine den Thread wechseln willst** (z. B. für IO, CPU-Arbeit).  
✅ **Wenn du auf ein Ergebnis warten möchtest** (im Gegensatz zu `launch`).  
✅ **Für Datenbank- oder Netzwerkzugriffe** (`Dispatchers.IO`).  
✅ **Für CPU-intensive Berechnungen** (`Dispatchers.Default`).  
❌ **Nicht für parallele Tasks – dafür `launch` oder `async` nutzen!** 🚀
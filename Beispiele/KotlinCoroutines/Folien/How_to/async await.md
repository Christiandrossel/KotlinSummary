### 🚀 **Kotlin Coroutines: `async` & `await`**

In Kotlin Coroutines werden `async` und `await` verwendet, um **asynchrone, parallele Berechnungen** durchzuführen und die Ergebnisse abzurufen.  
Sie bieten eine Möglichkeit, mehrere Aufgaben **parallel** auszuführen und später synchron auf deren Ergebnisse zuzugreifen.

---

### 📌 **1️⃣ Was ist `async`?**
- **`async {}`** startet eine Coroutine und gibt ein **`Deferred<T>`**-Objekt zurück.
- `Deferred` ist wie ein **Future** oder **Promise** in anderen Sprachen – es repräsentiert ein zukünftiges Ergebnis.
- Die Berechnung läuft **asynchron** im Hintergrund.

---

### 📌 **2️⃣ Was ist `await()`?**
- **`await()`** wird auf einem `Deferred`-Objekt aufgerufen, um auf das **Ergebnis zu warten**.
- Der Code **blockiert nicht den Thread**, sondern nur die Coroutine selbst, bis das Ergebnis verfügbar ist.

---

### ⚡ **Beispiel: Parallele Berechnungen mit `async` und `await`**

```kotlin
import kotlinx.coroutines.*

fun main() = runBlocking {
    println("Starte Berechnungen...")

    val deferred1 = async {
        delay(1000)  // Simuliert eine lange Berechnung
        println("Berechnung 1 abgeschlossen")
        10  // Ergebnis von Berechnung 1
    }

    val deferred2 = async {
        delay(1500)  // Simuliert eine noch längere Berechnung
        println("Berechnung 2 abgeschlossen")
        20  // Ergebnis von Berechnung 2
    }

    // Beide Berechnungen laufen parallel
    println("Warte auf Ergebnisse...")

    // Mit await() warten wir, bis beide Berechnungen abgeschlossen sind
    val result1 = deferred1.await()
    val result2 = deferred2.await()

    println("Gesamtergebnis: ${result1 + result2}")
}
```

---

### ✅ **Erwarteter Output:**

```
Starte Berechnungen...
Warte auf Ergebnisse...
Berechnung 1 abgeschlossen
Berechnung 2 abgeschlossen
Gesamtergebnis: 30
```

---

### 🚀 **Erklärung:**

1️⃣ **Paralleler Start:**
- `async` startet **sofort** beide Berechnungen parallel.
- `deferred1` und `deferred2` laufen gleichzeitig im Hintergrund.

2️⃣ **Nicht blockierend:**
- Die `delay()`-Aufrufe blockieren den Thread **nicht**, sondern nur die jeweilige Coroutine.

3️⃣ **Synchronisation mit `await()`:**
- `await()` wartet, bis das Ergebnis der Berechnungen verfügbar ist.
- Die Reihenfolge des Outputs zeigt, dass **`deferred1`** schneller abgeschlossen ist als **`deferred2`**.

4️⃣ **Effizient:**
- Da beide Berechnungen parallel laufen, ist die **Gesamtzeit** kürzer, als wenn sie nacheinander ausgeführt würden.

---

### 📊 **Vergleich zu `launch`:**

| 🔍 **Merkmal**        | 🚀 **`async`**                    | 🔥 **`launch`**                      |
|-----------------------|----------------------------------|--------------------------------------|
| **Rückgabewert**       | Gibt ein `Deferred<T>` zurück    | Gibt `Job` zurück (kein Ergebnis)    |
| **Verwendung**         | Für Berechnungen mit Rückgabe    | Für Fire-and-Forget-Aufgaben         |
| **Ergebnis abrufen**   | Mit `await()`                    | Kein Ergebnis verfügbar              |
| **Fehlerbehandlung**   | Fehler werden beim `await()` geworfen | Fehler werden direkt geworfen       |

---

### ⚡ **Optimierung: Gleichzeitiges `await`**

Statt nacheinander zu `await`-en, kannst du die Ergebnisse gleichzeitig abrufen:

```kotlin
val (result1, result2) = awaitAll(deferred1, deferred2)
println("Gesamtergebnis: ${result1 + result2}")
```

Dies verkürzt den Code und ist genauso effizient. 🚀

---

Wenn du mehr zu speziellen Anwendungsfällen wissen möchtest, sag Bescheid! 😎
Klar! Hier sind einige **praktische Beispiele**, die sich ausschließlich auf den **`launch`-Builder** konzentrieren, ohne den Einsatz von `async` oder `suspend`-Funktionen. Diese zeigen, wie du mit **`launch`** verschiedene parallele Aufgaben abwickeln kannst.

---

## 🚀 **Beispiel 1: Einfaches `launch` mit `runBlocking`**

```kotlin
import kotlinx.coroutines.*

fun main() = runBlocking {
    println("Start der Haupt-Coroutine")

    launch {
        delay(1000)
        println("Erste Coroutine abgeschlossen")
    }

    launch {
        delay(500)
        println("Zweite Coroutine abgeschlossen")
    }

    println("Ende der Haupt-Coroutine")
}
```

### ✅ **Erklärung:**
- **`runBlocking`** startet die Haupt-Coroutine und blockiert den aktuellen Thread, bis alle `launch`-Blöcke abgeschlossen sind.
- Die beiden `launch`-Blöcke laufen **parallel**, da sie innerhalb derselben Coroutine gestartet werden.

### 📊 **Output:**
```
Start der Haupt-Coroutine
Ende der Haupt-Coroutine
Zweite Coroutine abgeschlossen
Erste Coroutine abgeschlossen
```

> **Wichtig:** Obwohl „Ende der Haupt-Coroutine“ zuerst erscheint, läuft der Code asynchron weiter.

---

## ⏱️ **Beispiel 2: Verschachtelte `launch`-Blöcke**

```kotlin
import kotlinx.coroutines.*

fun main() = runBlocking {
    println("Start")

    launch {
        println("Äußere Coroutine gestartet")

        launch {
            delay(1000)
            println("Innere Coroutine 1 abgeschlossen")
        }

        launch {
            delay(500)
            println("Innere Coroutine 2 abgeschlossen")
        }

        println("Äußere Coroutine abgeschlossen")
    }

    println("Ende von runBlocking")
}
```

### 🔍 **Erklärung:**
- **Verschachtelung von `launch`:** Die inneren Coroutines werden innerhalb der äußeren Coroutine gestartet.
- Die äußere Coroutine wartet **implizit** auf alle inneren Coroutines, bevor sie vollständig abgeschlossen wird.

### 📊 **Output:**
```
Start
Äußere Coroutine gestartet
Ende von runBlocking
Innere Coroutine 2 abgeschlossen
Innere Coroutine 1 abgeschlossen
```

---

## 🔄 **Beispiel 3: Parallele Schleifen mit `launch`**

```kotlin
import kotlinx.coroutines.*

fun main() = runBlocking {
    repeat(5) { i ->
        launch {
            delay((i + 1) * 300L)
            println("Coroutine $i abgeschlossen nach ${((i + 1) * 300)}ms")
        }
    }
    println("Alle Coroutines gestartet")
}
```

### ⚡ **Erklärung:**
- Mit `repeat(5)` werden **5 parallele Coroutines** gestartet.
- Die Verzögerung (`delay`) ist für jede Coroutine unterschiedlich.

### 📊 **Output:**
```
Alle Coroutines gestartet
Coroutine 0 abgeschlossen nach 300ms
Coroutine 1 abgeschlossen nach 600ms
Coroutine 2 abgeschlossen nach 900ms
Coroutine 3 abgeschlossen nach 1200ms
Coroutine 4 abgeschlossen nach 1500ms
```

---

## ❌ **Beispiel 4: Fehlerbehandlung mit `launch`**

```kotlin
import kotlinx.coroutines.*

fun main() = runBlocking {
    val job = launch {
        try {
            println("Starte riskante Operation")
            throw RuntimeException("Ups! Ein Fehler ist aufgetreten.")
        } catch (e: Exception) {
            println("Fehler abgefangen: ${e.message}")
        } finally {
            println("Bereinige Ressourcen")
        }
    }

    job.join() // Warten, bis die Coroutine abgeschlossen ist
    println("Programm beendet")
}
```

### 🚩 **Erklärung:**
- **Fehler werden direkt in der `launch`-Coroutine behandelt**.
- `finally` wird immer ausgeführt, auch wenn ein Fehler auftritt.

### 📊 **Output:**
```
Starte riskante Operation
Fehler abgefangen: Ups! Ein Fehler ist aufgetreten.
Bereinige Ressourcen
Programm beendet
```

---

## 🌐 **Beispiel 5: Verwendung von Dispatchern mit `launch`**

```kotlin
import kotlinx.coroutines.*

fun main() = runBlocking {
    launch(Dispatchers.Default) {
        println("Läuft im Default-Dispatcher auf Thread: ${Thread.currentThread().name}")
    }

    launch(Dispatchers.IO) {
        println("Läuft im IO-Dispatcher auf Thread: ${Thread.currentThread().name}")
    }

    launch(Dispatchers.Unconfined) {
        println("Läuft im Unconfined-Dispatcher auf Thread: ${Thread.currentThread().name}")
    }

    launch(newSingleThreadContext("MeinEigenerThread")) {
        println("Läuft auf eigenem Thread: ${Thread.currentThread().name}")
    }
}
```

### 🧠 **Erklärung:**
- **`Dispatchers.Default`**: Für CPU-intensive Aufgaben.
- **`Dispatchers.IO`**: Für I/O-intensive Aufgaben (z.B. Netzwerk, DB).
- **`Dispatchers.Unconfined`**: Startet im aktuellen Thread, wechselt aber bei `suspend` ggf. den Thread.
- **`newSingleThreadContext`**: Erstellt einen dedizierten Thread.

### 📊 **Beispiel-Output:**
```
Läuft im Default-Dispatcher auf Thread: DefaultDispatcher-worker-1
Läuft im IO-Dispatcher auf Thread: DefaultDispatcher-worker-2
Läuft im Unconfined-Dispatcher auf Thread: main
Läuft auf eigenem Thread: MeinEigenerThread
```

---

## 🚀 **Beispiel 6: Global Scope (nicht empfohlen, aber wichtig zu kennen)**

```kotlin
import kotlinx.coroutines.*

fun main() {
    GlobalScope.launch {
        delay(1000)
        println("GlobalScope Coroutine abgeschlossen")
    }
    println("Programmende erreicht (wird möglicherweise beendet, bevor Coroutine fertig ist)")
    Thread.sleep(2000) // Notwendig, damit das Programm nicht sofort endet
}
```

### ⚠️ **Achtung:**
- **`GlobalScope`** startet eine Coroutine, die nicht an den Lebenszyklus des Aufrufers gebunden ist.
- **Nicht empfohlen**, da dies zu "laufenden" Coroutines führen kann, die nicht korrekt beendet werden.

### 📊 **Output:**
```
Programmende erreicht (wird möglicherweise beendet, bevor Coroutine fertig ist)
GlobalScope Coroutine abgeschlossen
```

> **💡 Tipp:** Verwende lieber `CoroutineScope` oder `runBlocking` für bessere Kontrolle.

---

## 🎯 **Zusammenfassung**

| **Begriff**       | **Beschreibung**                                   | **Beispiel**                  |
|-------------------|----------------------------------------------------|-------------------------------|
| `runBlocking`     | Blockiert den Thread bis zum Abschluss der Coroutines | `runBlocking { launch { } }` |
| `launch`          | Startet eine neue Coroutine, die kein Ergebnis zurückgibt | `launch { println("Hallo") }` |
| `Dispatchers.IO`  | Optimiert für I/O-Operationen                        | `launch(Dispatchers.IO) { }` |
| `Dispatchers.Default` | Für CPU-intensive Aufgaben                     | `launch(Dispatchers.Default)` |
| `GlobalScope`     | Coroutine lebt unabhängig vom Lebenszyklus des Callers | `GlobalScope.launch { }`     |
| `supervisorScope` | Fehler in einer Coroutine stoppen nicht andere     | `supervisorScope { launch { } }` |

---

Möchtest du noch tiefer in ein bestimmtes Thema eintauchen? 😊
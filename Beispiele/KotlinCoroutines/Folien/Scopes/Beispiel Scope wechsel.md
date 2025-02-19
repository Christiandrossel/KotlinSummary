Hier sind **Beispiele mit `runBlocking` und `launch`**, in denen **verschiedene Coroutine-Scopes** verwendet werden. Ich erkläre dir dabei, was passiert und wie sich das Verhalten durch den **Scope-Wechsel** ändert.

---

## 🚀 **Beispiel 1: Wechsel des Dispatchers im `launch`**

```kotlin
import kotlinx.coroutines.*

fun main() = runBlocking {
    println("Start in Thread: ${Thread.currentThread().name}")

    // Coroutine im Main-Thread (runBlocking-Context)
    launch {
        println("RunBlocking Scope: ${Thread.currentThread().name}")
    }

    // Coroutine im Default-Dispatcher (für CPU-intensive Tasks)
    launch(Dispatchers.Default) {
        println("Default Dispatcher: ${Thread.currentThread().name}")
    }

    // Coroutine im IO-Dispatcher (für I/O-intensive Tasks)
    launch(Dispatchers.IO) {
        println("IO Dispatcher: ${Thread.currentThread().name}")
    }

    println("Ende in Thread: ${Thread.currentThread().name}")
}
```

### 🧠 **Erklärung:**
- **`runBlocking`-Scope:** Startet auf dem Haupt-Thread (hier `main`).
- **`Dispatchers.Default`:** Nutzt einen Hintergrund-Thread (optimal für CPU-intensive Tasks).
- **`Dispatchers.IO`:** Verwendet einen Thread aus einem I/O-optimierten Pool.

### 📊 **Möglicher Output:**
```
Start in Thread: main
Ende in Thread: main
RunBlocking Scope: main
Default Dispatcher: DefaultDispatcher-worker-1
IO Dispatcher: DefaultDispatcher-worker-2
```

> **Wichtig:** Obwohl `launch`-Blöcke parallel starten, erfolgt der `println` im `runBlocking`-Scope sofort, da er synchron läuft.

---

## 🌐 **Beispiel 2: Verwenden eines eigenen `CoroutineScope`**

```kotlin
import kotlinx.coroutines.*

fun main() = runBlocking {
    println("Start der runBlocking-Coroutine")

    val customScope = CoroutineScope(Dispatchers.Default)

    launch {
        println("Innerhalb runBlocking: ${Thread.currentThread().name}")
    }

    customScope.launch {
        println("Innerhalb customScope (Default Dispatcher): ${Thread.currentThread().name}")
    }

    delay(500) // Warten, damit customScope fertig wird
    println("Ende von runBlocking")
}
```

### 🔍 **Erklärung:**
- **`runBlocking`** verwendet den Main-Thread.
- **`customScope`** verwendet `Dispatchers.Default`, also einen separaten Hintergrund-Thread.

### 📊 **Output:**
```
Start der runBlocking-Coroutine
Innerhalb runBlocking: main
Innerhalb customScope (Default Dispatcher): DefaultDispatcher-worker-1
Ende von runBlocking
```

> **Unterschied:** `customScope`-Coroutines leben unabhängig von `runBlocking`. Ohne `delay` könnte der `runBlocking`-Block enden, bevor `customScope` abgeschlossen ist.

---

## ⚠️ **Beispiel 3: Scope-Wechsel mit `withContext`**

```kotlin
import kotlinx.coroutines.*

fun main() = runBlocking {
    println("Start: ${Thread.currentThread().name}")

    launch {
        println("Vor Scope-Wechsel: ${Thread.currentThread().name}")
        withContext(Dispatchers.IO) {
            println("Nach Scope-Wechsel (IO): ${Thread.currentThread().name}")
        }
        println("Zurück im ursprünglichen Scope: ${Thread.currentThread().name}")
    }

    println("Ende von runBlocking: ${Thread.currentThread().name}")
}
```

### 💡 **Erklärung:**
- **`withContext`** ändert temporär den Dispatcher (hier zu `Dispatchers.IO`).
- Nach `withContext` kehrt der Code automatisch in den ursprünglichen Scope zurück.

### 📊 **Output:**
```
Start: main
Ende von runBlocking: main
Vor Scope-Wechsel: main
Nach Scope-Wechsel (IO): DefaultDispatcher-worker-1
Zurück im ursprünglichen Scope: main
```

> **Fazit:** `withContext` blockiert die Coroutine, bis der Block abgeschlossen ist, wechselt aber nahtlos zurück.

---

## 🚀 **Beispiel 4: GlobalScope vs. runBlocking Scope**

```kotlin
import kotlinx.coroutines.*

fun main() = runBlocking {
    println("Start von runBlocking")

    launch {
        println("Innerhalb runBlocking: ${Thread.currentThread().name}")
    }

    GlobalScope.launch {
        delay(1000)
        println("Innerhalb GlobalScope: ${Thread.currentThread().name}")
    }

    println("Ende von runBlocking (wartet nicht auf GlobalScope)")
    delay(1500) // Notwendig, um GlobalScope-Coroutine abzuwarten
}
```

### ⚠️ **Erklärung:**
- **`GlobalScope`** ist nicht an den Lebenszyklus von `runBlocking` gebunden.
- Ohne `delay(1500)` würde `runBlocking` enden, bevor `GlobalScope` seine Arbeit beendet.

### 📊 **Output:**
```
Start von runBlocking
Ende von runBlocking (wartet nicht auf GlobalScope)
Innerhalb runBlocking: main
Innerhalb GlobalScope: DefaultDispatcher-worker-1
```

> **Best Practice:** GlobalScope nur verwenden, wenn es absolut notwendig ist. Andernfalls lieber `CoroutineScope`.

---

## 📊 **Beispiel 5: Unterschiedliche Scopes in verschachtelten `launch`-Blöcken**

```kotlin
import kotlinx.coroutines.*

fun main() = runBlocking {
    println("Start von runBlocking")

    launch(Dispatchers.Default) {
        println("Äußere Coroutine (Default): ${Thread.currentThread().name}")

        launch(Dispatchers.IO) {
            println("Innere Coroutine (IO): ${Thread.currentThread().name}")
        }
    }

    println("Ende von runBlocking")
    delay(500) // Damit alle Coroutines abgeschlossen werden
}
```

### 🔍 **Erklärung:**
- Der äußere `launch` läuft im **Default-Dispatcher**.
- Die innere Coroutine läuft im **IO-Dispatcher** – **trotz Verschachtelung** hat sie einen eigenen Scope.

### 📊 **Output:**
```
Start von runBlocking
Ende von runBlocking
Äußere Coroutine (Default): DefaultDispatcher-worker-1
Innere Coroutine (IO): DefaultDispatcher-worker-2
```

> **Erkenntnis:** Auch verschachtelte Coroutines können unterschiedliche Dispatcher verwenden.

---

## 🎯 **Wichtige Erkenntnisse:**

| **Scope/Dispatcher**         | **Beschreibung**                                                    | **Beispiel**                      |
|------------------------------|--------------------------------------------------------------------|-----------------------------------|
| `runBlocking`                | Blockiert den aktuellen Thread (synchron)                           | `runBlocking { launch { } }`     |
| `launch(Dispatchers.Default)`| Für CPU-intensive Aufgaben (Hintergrund-Thread)                     | `launch(Dispatchers.Default)`    |
| `launch(Dispatchers.IO)`     | Für I/O-intensive Aufgaben (z.B. Netzwerk, DB)                      | `launch(Dispatchers.IO)`         |
| `withContext(Dispatcher)`    | Temporärer Scope-Wechsel, blockiert bis zum Abschluss               | `withContext(Dispatchers.IO)`    |
| `GlobalScope`                | Coroutine lebt unabhängig vom Lebenszyklus des Callers (Achtung!)   | `GlobalScope.launch { }`         |
| `CoroutineScope`             | Erzeugt einen Scope, an den Coroutines gebunden sind               | `CoroutineScope(Dispatcher)`     |

---

## ❓ **Hast du noch Fragen zu einem speziellen Szenario?**
Ich kann dir auch komplexere Beispiele oder Performance-Details zeigen! 🚀
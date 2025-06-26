# Tutorial: Kotlin Coroutines und eigene Dispatcher

Kotlin Coroutines ermöglichen nebenläufige und asynchrone Programmierung auf einfache Weise. Ein zentraler Baustein ist der **Coroutine Dispatcher**, der bestimmt, auf welchem Thread oder Threadpool eine Coroutine ausgeführt wird.

---

## Was ist ein Dispatcher?
Ein Dispatcher steuert, wie und wo Coroutines laufen:
- **Dispatchers.Default**: Für CPU-intensive Aufgaben (Standard-Threadpool)
- **Dispatchers.IO**: Für blockierende I/O-Aufgaben (z.B. Datenbank, Netzwerk)
- **Dispatchers.Main**: Für UI-Operationen (Android/JavaFX)
- **Eigene Dispatcher**: Für spezielle Anforderungen, z.B. limitierte Threadpools

---

## Beispiel: Eigener Dispatcher mit Threadpool

Manchmal möchte man die Parallelität gezielt steuern, z.B. um externe Systeme nicht zu überlasten. Dafür kann man einen eigenen Dispatcher anlegen:

```kotlin
import kotlinx.coroutines.asCoroutineDispatcher
import java.util.concurrent.Executors

val dispatcher = Executors.newFixedThreadPool(12).asCoroutineDispatcher()
```

Verwendung in einer Coroutine:

```kotlin
runBlocking(dispatcher) {
    // parallele Aufgaben
}
```

---

## Weitere Dispatcher-Möglichkeiten

### 1. Standard-Dispatcher
```kotlin
import kotlinx.coroutines.Dispatchers

runBlocking(Dispatchers.Default) {
    // Für CPU-intensive Aufgaben
}
```

### 2. IO-Dispatcher
```kotlin
runBlocking(Dispatchers.IO) {
    // Für I/O-intensive Aufgaben
}
```

### 3. Unconfined Dispatcher
```kotlin
import kotlinx.coroutines.Dispatchers

runBlocking(Dispatchers.Unconfined) {
    // Startet im aktuellen Thread, wechselt ggf. später
}
```

### 4. Single-Threaded Dispatcher
```kotlin
val singleThreadDispatcher = Executors.newSingleThreadExecutor().asCoroutineDispatcher()
runBlocking(singleThreadDispatcher) {
    // Läuft immer auf demselben Thread
}
```

---

## Best Practices
- Nutze eigene Dispatcher, wenn du die Parallelität gezielt begrenzen willst (z.B. für externe Services).
- Vergiss nicht, eigene Dispatcher nach Gebrauch mit `dispatcher.close()` zu schließen!
- Für Standardfälle reichen meist `Dispatchers.Default` und `Dispatchers.IO`.

---

## Fazit
Mit Coroutines und Dispatchern kannst du in Kotlin sehr flexibel und performant nebenläufige Aufgaben steuern. Eigene Dispatcher sind ideal, wenn du volle Kontrolle über die Threadanzahl und Ausführung brauchst.


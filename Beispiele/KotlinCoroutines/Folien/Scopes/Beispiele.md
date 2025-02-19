Natürlich! Hier sind einige **praktische Beispiele** für den Einsatz von **Kotlin Coroutines in einer Spring Boot-Anwendung**. Ich zeige dir verschiedene Szenarien, damit du die Konzepte von **Scopes**, **Dispatchers** und **Fehlerbehandlung** besser verstehst.

---

## 🚀 **Beispiel 1: Einfaches Spring Boot Service mit Coroutines**

### 📦 **1️⃣ Service mit `CoroutineScope`**

```kotlin
import kotlinx.coroutines.*
import org.springframework.stereotype.Service

@Service
class UserService : CoroutineScope {
    private val job = SupervisorJob()
    override val coroutineContext = Dispatchers.IO + job

    fun fetchUsers() = launch {
        val users = getUsersFromDatabase()
        println("Benutzer: $users")
    }

    private suspend fun getUsersFromDatabase(): List<String> {
        delay(1000) // Simuliert eine DB-Abfrage
        return listOf("Alice", "Bob", "Charlie")
    }
}
```

### ✅ **Was passiert hier?**
- **`CoroutineScope`** wird direkt in der Service-Klasse erstellt.
- `SupervisorJob` sorgt dafür, dass ein Fehler **nicht alle anderen Coroutines** beendet.
- **`Dispatchers.IO`** ist perfekt für I/O-Operationen wie Datenbankzugriffe.

---

## ⚡ **Beispiel 2: Parallele API-Requests mit `async/await`**

### 🌐 **2️⃣ Daten parallel von mehreren APIs abrufen**

```kotlin
import kotlinx.coroutines.*
import org.springframework.stereotype.Service

@Service
class ApiService {

    suspend fun fetchData() = coroutineScope {
        val api1 = async { fetchFromApi1() }
        val api2 = async { fetchFromApi2() }

        println("API 1: ${api1.await()}")
        println("API 2: ${api2.await()}")
    }

    private suspend fun fetchFromApi1(): String {
        delay(2000) // Simuliert eine API-Abfrage
        return "Daten von API 1"
    }

    private suspend fun fetchFromApi2(): String {
        delay(1500)
        return "Daten von API 2"
    }
}
```

### 🚀 **Erklärung:**
- **`async` + `await`:** Führt beide API-Calls **parallel** aus.
- **`coroutineScope`:** Sorgt dafür, dass der Code erst weiterläuft, wenn beide `async`-Tasks fertig sind.

### 📊 **Output:**
Nach 2 Sekunden (nicht 3, da parallel):
```
API 2: Daten von API 2
API 1: Daten von API 1
```

---

## 🔥 **Beispiel 3: Fehlerisolierung mit `supervisorScope`**

### 💥 **3️⃣ Fehlerbehandlung bei parallelen Tasks**

```kotlin
import kotlinx.coroutines.*
import org.springframework.stereotype.Service

@Service
class ErrorHandlingService {

    suspend fun processTasks() = supervisorScope {
        val job1 = launch {
            delay(1000)
            println("Task 1 abgeschlossen")
        }

        val job2 = launch {
            delay(500)
            throw RuntimeException("Fehler in Task 2")
        }

        val job3 = launch {
            delay(1500)
            println("Task 3 abgeschlossen")
        }
    }
}
```

### 🚩 **Wichtige Punkte:**
- **`supervisorScope`:** Ein Fehler in `job2` **beendet NICHT** `job1` oder `job3`.
- Ohne `supervisorScope` würde der Fehler alle anderen Coroutines stoppen.

### 📊 **Output:**
```
Task 1 abgeschlossen
Exception in Task 2
Task 3 abgeschlossen
```

---

## 🗂️ **Beispiel 4: Verwendung von `@Async` mit `suspend` in Spring Boot**

### 🔄 **4️⃣ Asynchrone Services kombinieren**

```kotlin
import kotlinx.coroutines.delay
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

@Service
class AsyncService {

    @Async
    suspend fun asyncTask1() {
        delay(1000)
        println("Async Task 1 abgeschlossen")
    }

    @Async
    suspend fun asyncTask2() {
        delay(1500)
        println("Async Task 2 abgeschlossen")
    }
}
```

### 🚀 **Verwendung in einem Controller:**

```kotlin
import kotlinx.coroutines.runBlocking
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AsyncController(val asyncService: AsyncService) {

    @GetMapping("/run-tasks")
    fun runTasks() = runBlocking {
        asyncService.asyncTask1()
        asyncService.asyncTask2()
        "Alle Tasks abgeschlossen"
    }
}
```

### 🌍 **Erklärung:**
- **`@Async` + `suspend`:** Führt die Aufgaben asynchron aus.
- **`runBlocking`:** Blockiert nur den Request-Thread, nicht den gesamten Server.

---

## 🧵 **Beispiel 5: Eigener Dispatcher (Custom Thread Pool)**

### 🔧 **5️⃣ Custom Dispatcher erstellen**

```kotlin
import kotlinx.coroutines.*
import java.util.concurrent.Executors

fun main() = runBlocking {
    val customDispatcher = Executors.newFixedThreadPool(4).asCoroutineDispatcher()

    withContext(customDispatcher) {
        repeat(4) { i ->
            launch {
                println("Task $i läuft auf Thread: ${Thread.currentThread().name}")
            }
        }
    }
}
```

### 🚀 **Erklärung:**
- **`Executors.newFixedThreadPool(4)`**: Erstellt einen eigenen Thread-Pool mit 4 Threads.
- **`asCoroutineDispatcher()`**: Wandelt den Executor in einen Coroutine Dispatcher um.

### 📊 **Output:**
```
Task 0 läuft auf Thread: pool-1-thread-1
Task 1 läuft auf Thread: pool-1-thread-2
Task 2 läuft auf Thread: pool-1-thread-3
Task 3 läuft auf Thread: pool-1-thread-4
```

---

## ✅ **Best Practices (Spring Boot mit Coroutines)**

1. **Nutze `CoroutineScope` mit `SupervisorJob` für Services.**
2. **Verwende `Dispatchers.IO` für Datenbank- oder API-Calls.**
3. **Nutze `async/await` für parallele Aufgaben.**
4. **Fehlerisolierung? → `supervisorScope` verwenden!**
5. **Für einfache Async-Tasks: Kombiniere `@Async` mit `suspend`.**

---

Möchtest du zu einem bestimmten Beispiel noch mehr Details? 😊
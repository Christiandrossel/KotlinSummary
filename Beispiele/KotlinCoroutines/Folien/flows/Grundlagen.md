### ✅ **Was sind `Flow` in Kotlin Coroutines?**
Ein `Flow` in Kotlin ist ein **asynchroner Datenstrom**, der mehrere Werte nacheinander **emittieren** kann – ähnlich wie eine `List`, aber reaktiv und nicht-blockierend.

**Vergleich:**
- `suspend fun` gibt **einen Wert** zurück.
- `Flow` gibt **mehrere Werte** nacheinander aus, ähnlich einem `Sequence` oder `Stream`.

---

## 🚀 **1. Einfache Verwendung von `Flow`**
```kotlin
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun main() = runBlocking {
    simpleFlow().collect { value ->
        println("Empfangen: $value")
    }
}

fun simpleFlow(): Flow<Int> = flow {
    for (i in 1..5) {
        delay(500) // Simuliert eine Verzögerung
        emit(i)    // Sendet den Wert weiter
    }
}
```
**Ergebnis:**
```
Empfangen: 1
Empfangen: 2
Empfangen: 3
Empfangen: 4
Empfangen: 5
```

---

## ⚡ **2. Warum `Flow` nutzen?**
✅ **Nicht-blockierend:** Läuft asynchron, ohne einen Thread zu blockieren.  
✅ **Mehrere Werte:** Kann **beliebig viele Werte** senden (anders als `suspend fun`).  
✅ **Reaktive Verarbeitung:** Unterstützt **Transformationen wie `map`, `filter`**.  
✅ **Backpressure-Unterstützung:** **Verlangsamt** die Datenproduktion, wenn nötig.

---

## 🔥 **3. Operatoren mit `Flow`**
Ähnlich zu `Sequence` oder `Stream` in Java bietet `Flow` viele Operatoren.

### 🔹 **3.1 `map`: Werte transformieren**
```kotlin
fun transformedFlow(): Flow<String> = flow {
    emit(1)
    emit(2)
    emit(3)
}.map { value -> "Wert: $value" }

fun main() = runBlocking {
    transformedFlow().collect { println(it) }
}
```
**Ergebnis:**
```
Wert: 1
Wert: 2
Wert: 3
```

---

### 🔹 **3.2 `filter`: Werte filtern**
```kotlin
fun filteredFlow(): Flow<Int> = flow {
    emit(1)
    emit(2)
    emit(3)
    emit(4)
}.filter { it % 2 == 0 }

fun main() = runBlocking {
    filteredFlow().collect { println(it) }
}
```
**Ergebnis:**
```
2
4
```

---

### 🔹 **3.3 `buffer()`: Parallelität erhöhen**
```kotlin
fun main() = runBlocking {
    val time = measureTimeMillis {
        flow {
            for (i in 1..3) {
                delay(500)
                emit(i)
            }
        }
        .buffer() // Erhöht Parallelität
        .collect { value ->
            delay(500)
            println("Empfangen: $value")
        }
    }
    println("Gesamtdauer: $time ms")
}
```
**Ohne `buffer()`**: 1500ms  
**Mit `buffer()`**: ~1000ms

🔹 **Warum?**
- `buffer()` erlaubt **Produktion & Konsum in verschiedenen Coroutines**.
- Ohne `buffer()` läuft alles **sequentiell**, mit `buffer()` **parallel**.

---

## 🔄 **4. `SharedFlow` vs. `StateFlow`**
Neben `Flow` gibt es **hot Flows**, die **permanent Werte speichern**.

| Typ         | Hot/Cold? | Behält letzten Wert? | Mehrere Empfänger? | Beispiel |
|------------|----------|------------------|----------------|----------|
| `Flow`     | ❄ **Cold** | ❌ Nein | ✅ Ja | API-Datenstrom |
| `SharedFlow` | 🔥 **Hot** | ❌ Nein | ✅ Ja | EventBus |
| `StateFlow` | 🔥 **Hot** | ✅ Ja | ✅ Ja | UI-State |

### **Wann `StateFlow`?**
- Perfekt für **UI-Status in MVVM** → Erhält immer den letzten Wert.
```kotlin
val stateFlow = MutableStateFlow(0)

fun main() = runBlocking {
    launch {
        stateFlow.collect { println("StateFlow: $it") }
    }
    delay(1000)
    stateFlow.value = 1
    delay(1000)
    stateFlow.value = 2
}
```

---

## 🔥 **5. Fazit**
✅ `Flow` ist **perfekt für asynchrone Datenströme**.  
✅ Verhindert **Thread-Blocking** durch **nicht-blockierende Verarbeitung**.  
✅ `buffer()`, `map()`, `filter()` bieten mächtige Operatoren.  
✅ **`SharedFlow` & `StateFlow
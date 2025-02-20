## **🚀 Flow, StateFlow & SharedFlow in Kotlin Coroutines**
In Kotlin gibt es drei wichtige Arten von **asynchronen Datenströmen**:

| **Typ**       | **Eigenschaft** | **Kalt oder Heiß?** | **Mehrere Konsumenten?** | **Behält letzten Wert?** |
|--------------|----------------|----------------------|--------------------------|--------------------------|
| `Flow`       | Sequenzielle Werte | **Cold (kalt)** | ❌ Nein | ❌ Nein |
| `StateFlow`  | Zustandsverlauf | **Hot (heiß)** | ✅ Ja | ✅ Ja |
| `SharedFlow` | Broadcast-Stream | **Hot (heiß)** | ✅ Ja | ❌ Optional |

---

## **📌 1. Flow – Der klassische asynchrone Datenstrom**
Ein **`Flow` ist "Cold"**, das heißt:
- Die Daten werden erst **bei `collect()` produziert**.
- Jeder neue Konsument **startet den Stream von vorne**.
- Es gibt **kein Zwischenspeichern von Werten**.

### **🔍 Beispiel: `Flow` (Cold Stream)**
```kotlin
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun main() = runBlocking {
    val myFlow = flow {
        for (i in 1..3) {
            delay(1000)
            emit(i)
        }
    }

    println("Starte ersten Collector")
    myFlow.collect { println("Collector 1: $it") }

    println("Starte zweiten Collector")
    myFlow.collect { println("Collector 2: $it") }
}
```
### **🔍 Ausgabe**
```
Starte ersten Collector  
(1 Sekunde Wartezeit) → Collector 1: 1  
(1 Sekunde Wartezeit) → Collector 1: 2  
(1 Sekunde Wartezeit) → Collector 1: 3  

Starte zweiten Collector  
(1 Sekunde Wartezeit) → Collector 2: 1  
(1 Sekunde Wartezeit) → Collector 2: 2  
(1 Sekunde Wartezeit) → Collector 2: 3  
```
👉 Jeder `collect()`-Aufruf startet den Flow von vorne!

---

## **📌 2. StateFlow – Behält den letzten Zustand (Zustands-Flow)**
Ein **`StateFlow` ist ein "Hot Stream"**, das heißt:
- Es speichert immer den **letzten Wert** und sendet ihn an neue Konsumenten.
- Es startet **sofort** (ohne dass ein `collect()` nötig ist).
- Es eignet sich für **Zustandsmanagement in UI & Live-Daten**.

### **🔍 Beispiel: `StateFlow` (Hot Stream mit letzten Zustand)**
```kotlin
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun main() = runBlocking {
    val stateFlow = MutableStateFlow(0) // Startwert ist 0

    launch {
        delay(1000)
        stateFlow.value = 1
        delay(1000)
        stateFlow.value = 2
    }

    delay(1500) // Warten, bevor der Collector startet
    stateFlow.collect { println("Collector: $it") }
}
```
### **🔍 Ausgabe**
```
Collector: 1  
(1 Sekunde Wartezeit) → Collector: 2  
```
👉 **Der Collector startet erst später, bekommt aber direkt den letzten Wert (1)**!

---

## **📌 3. SharedFlow – Für mehrere Konsumenten (Broadcast-Stream)**
Ein **`SharedFlow` ist wie ein `Flow`, aber für mehrere Konsumenten gleichzeitig**:
- Es speichert **nicht automatisch** den letzten Wert (außer mit `replay`).
- Mehrere Konsumenten bekommen **dieselben Daten gleichzeitig**.
- Gut für **Event-Streams, Websockets, oder Live-Daten mit mehreren Listenern**.

### **🔍 Beispiel: `SharedFlow` (Hot Stream mit Broadcast-Funktionalität)**
```kotlin
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun main() = runBlocking {
    val sharedFlow = MutableSharedFlow<Int>()

    launch {
        for (i in 1..3) {
            delay(500)
            sharedFlow.emit(i) // Sendet Werte
        }
    }

    launch {
        sharedFlow.collect { println("Collector 1: $it") }
    }

    launch {
        delay(1000) // Collector 2 startet später
        sharedFlow.collect { println("Collector 2: $it") }
    }
}
```
### **🔍 Ausgabe**
```
Collector 1: 1  
Collector 1: 2  
Collector 2: 2  
Collector 1: 3  
Collector 2: 3  
```
👉 **Collector 2 verpasst den ersten Wert, weil `SharedFlow` keine Werte speichert!**

Falls du möchtest, dass neue Konsumenten **alte Werte sehen**, verwende `replay`:

```kotlin
val sharedFlow = MutableSharedFlow<Int>(replay = 1)
```
Jetzt bekommen neue Konsumenten den letzten Wert automatisch!

---

## **📝 Zusammenfassung**
| **Typ**       | **Cold/Hot** | **Mehrere Konsumenten?** | **Speichert Werte?** | **Einsatzbereich** |
|--------------|-------------|--------------------------|----------------------|--------------------|
| `Flow`       | **Cold**  | ❌ Nein (jeder fängt von vorne an) | ❌ Nein | **Datenströme mit sequentiellen Werten** |
| `StateFlow`  | **Hot**   | ✅ Ja | ✅ Ja (letzter Wert) | **UI-Status, Live-Daten, ViewModel in Jetpack Compose** |
| `SharedFlow` | **Hot**   | ✅ Ja | ❌ Nein (außer mit `replay`) | **Event-Streams, WebSockets, Multicast-Daten** |

---

🚀 **Wann solltest du was benutzen?**  
✅ **`Flow`**, wenn du eine **Datenquelle mit mehreren Werten über die Zeit** hast.  
✅ **`StateFlow`**, wenn du **immer den aktuellen Zustand speichern & abrufen** möchtest.  
✅ **`SharedFlow`**, wenn du **Ereignisse an mehrere Konsumenten gleichzeitig senden** möchtest.

🔥 **Kotlin Coroutines + `Flow`, `StateFlow` und `SharedFlow` machen asynchrone Programmierung extrem effizient!**
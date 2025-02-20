# Was ist Flow?
* Ein Datenstrom, der asynchron berechnet werden kann, wird konzeptionell als Flow bezeichnet.
* Es wird mithilfe von Coroutinen erstellt. Ein geeigneter Kotlin-Typ zum Modellieren von Datenströmen ist Flow.
* Flow ermöglicht Ihnen wie LiveData- und RxJava-Streams die Implementierung des Beobachtermusters: 
ein Software-Entwurfsmuster, das aus einem Objekt (Quelle) besteht, das eine Liste seiner abhängigen Objekte, 
sogenannte Beobachter (Sammler), führt und sie automatisch über Statusänderungen benachrichtigt.
Ein Flow verwendet angehaltene Funktionen, um asynchron zu konsumieren und zu produzieren.
  


Wir können sagen, dass Flow die Vorteile von LiveData und RxJava nutzt.


# Funktionsweise des Flows: Am Flow beteiligte Entitäten

## **📌 Was ist `Flow` in Kotlin Coroutines?**
`Flow` ist eine **asynchrone Datenstrom-Abstraktion** in Kotlin, die **mehrere Werte sequenziell über die Zeit liefert**. Es ist das Coroutine-Äquivalent zu `Sequence`, aber **nicht-blockierend** und unterstützt **suspendable Funktionen**.

---

## **🛠 Wie funktioniert `Flow`?**
1. **Produktion von Werten** (`emit()`)
    - Eine `Flow`-Funktion (`flow {}`) kann mehrere Werte **nacheinander senden**.

2. **Zwischenoperationen** (`map`, `filter`, `transform`)
    - Werte können **verändert oder gefiltert** werden, bevor sie konsumiert werden.

3. **Konsumieren der Werte** (`collect()`)
    - `collect()` ruft die Werte aus dem `Flow` ab und verarbeitet sie.

---

## **🚀 Beispiel: Einfacher `Flow`**
```kotlin
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun main() = runBlocking {
    val numbersFlow: Flow<Int> = flow {
        for (i in 1..5) {
            delay(500) // Simuliert eine Verzögerung
            emit(i)   // Sendet einen Wert
        }
    }

    println("Starte Flow-Konsum...")
    numbersFlow.collect { value ->
        println("Empfangen: $value")
    }
}
```
### **🔍 Was passiert hier?**
- Die `flow {}`-Funktion **emittiert** (`emit()`) Werte von 1 bis 5 mit `delay(500)`.
- `collect {}` empfängt jeden Wert **nacheinander** und verarbeitet ihn.
- **Nicht-blockierend!** → `delay(500)` blockiert den Thread nicht.

---

## **🔄 Operatoren in `Flow` (Transformation & Filter)**
Mit `map`, `filter` und `transform` kannst du Werte modifizieren oder filtern.

### **📌 Beispiel: `map` & `filter`**
```kotlin
fun main() = runBlocking {
    val numbersFlow = flow {
        for (i in 1..5) {
            emit(i)
        }
    }
    
    numbersFlow
        .filter { it % 2 == 1 } // Nur ungerade Zahlen
        .map { it * 10 }         // Werte umwandeln
        .collect { println("Empfangen: $it") }
}
```
### **🔍 Ergebnis**
```
Empfangen: 10
Empfangen: 30
Empfangen: 50
```
👉 Nur ungerade Zahlen (`1, 3, 5`), multipliziert mit `10`.

---

## **⏳ `Flow` ist sequentiell! (`collect` blockiert den Aufrufer)**
Standardmäßig wird `Flow` **sequentiell ausgeführt**. Das bedeutet, dass `collect()` erst **fertig sein muss**, bevor der nächste Wert verarbeitet wird.

### **📌 Beispiel: Langsame Verarbeitung**
```kotlin
fun main() = runBlocking {
    val slowFlow = flow {
        for (i in 1..3) {
            delay(1000) // Simuliert langsame Berechnung
            emit(i)
        }
    }

    slowFlow.collect { value ->
        delay(1000) // Langsame Verarbeitung pro Wert
        println("Empfangen: $value")
    }
}
```
### **🔍 Ergebnis**
```
(1 Sekunde Wartezeit) -> Empfangen: 1  
(1 Sekunde Wartezeit) -> Empfangen: 2  
(1 Sekunde Wartezeit) -> Empfangen: 3  
```
**Gesamtdauer: ~6 Sekunden!**  
👉 **`collect {}` verarbeitet Werte nacheinander und blockiert den `Flow`.**

---

## **🚀 `Flow` parallel verarbeiten mit `buffer()` oder `conflate()`**
Falls du nicht möchtest, dass `collect()` die Produktion blockiert, kannst du `buffer()` oder `conflate()` verwenden.

### **📌 Beispiel: `buffer()` (Produktion und Konsumieren entkoppeln)**
```kotlin
fun main() = runBlocking {
    val bufferedFlow = flow {
        for (i in 1..3) {
            delay(1000)
            emit(i)
        }
    }.buffer() // Werte werden zwischengespeichert

    bufferedFlow.collect { value ->
        delay(2000) // Langsame Verarbeitung
        println("Empfangen: $value")
    }
}
```
### **🔍 Ergebnis**
```
(1 Sekunde Wartezeit) -> Empfangen: 1  
(Produktion läuft im Hintergrund weiter)  
(2 Sekunden Wartezeit) -> Empfangen: 2  
(2 Sekunden Wartezeit) -> Empfangen: 3  
```
👉 **Durch `buffer()` kann der `Flow` schneller Werte produzieren, während `collect()` langsamer verarbeitet.**

---

## **🚀 `SharedFlow` & `StateFlow` (Hot Streams)**
**`Flow` ist ein "Cold Stream"**, das bedeutet:
- Die Werte werden **erst erzeugt**, wenn `collect()` aufgerufen wird.
- Jeder `collect()`-Aufruf startet **den `Flow` von vorne**.

Falls du einen **Hot Stream** brauchst (der unabhängig von `collect()` weiterläuft), verwende **`SharedFlow` oder `StateFlow`**.

### **📌 Beispiel: `SharedFlow` (Multicast-Flow für mehrere Konsumenten)**
```kotlin
fun main() = runBlocking {
    val sharedFlow = MutableSharedFlow<Int>()

    launch {
        for (i in 1..5) {
            delay(500)
            sharedFlow.emit(i) // Werte senden
        }
    }

    launch {
        sharedFlow.collect { value ->
            println("Konsument 1: $value")
        }
    }

    launch {
        sharedFlow.collect { value ->
            println("Konsument 2: $value")
        }
    }
}
```
**Hier bekommen beide Konsumenten gleichzeitig dieselben Werte!**  
👉 **`Flow` ist "Cold", aber `SharedFlow` ist "Hot"!**

---

## **📝 Fazit**
- **`Flow` ist ein asynchroner Datenstrom**, der **mehrere Werte über die Zeit liefert**.
- **Sequentielle Verarbeitung** → `collect()` blockiert den `Flow`, solange es läuft.
- **Nicht-blockierend** → `delay()` pausiert die Coroutine, **aber nicht den Thread**.
- **Transformation mit `map()`, `filter()`, `buffer()` möglich**.
- **Cold vs. Hot Streams** → `Flow` startet bei `collect()`, `SharedFlow` läuft unabhängig.

🚀 **Kurz gesagt: `Flow` ist der beste Weg, um mehrere asynchrone Werte zu verwalten!**
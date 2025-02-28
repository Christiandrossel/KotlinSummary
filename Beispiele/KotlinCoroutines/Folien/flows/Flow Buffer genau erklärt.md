## **📌 `buffer(capacity: Int)` in Kotlin Flow – Was macht die Kapazität?**

Die `capacity` von `buffer()` bestimmt, **wie viele Werte maximal gepuffert werden können, bevor `emit()` blockiert**.
- **Wenn der Puffer voll ist**, muss der `emit()`-Aufrufer **warten**, bis ein Wert von `collect()` verarbeitet wird.
- **Wenn `capacity` hoch ist**, kann der Produzent mehr Werte vorbereiten, bevor `collect()` beginnt.
- **Wenn `capacity` niedrig ist**, kann `emit()` öfter blockiert werden.

---

## **🚀 1. Beispiel: `buffer(capacity = 2)`**
```kotlin
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun main() = runBlocking {
    flow {
        for (i in 1..5) {
            delay(500) // Produziert schneller
            emit(i)
            println("🔵 Emitted: $i")
        }
    }
    .buffer(capacity = 2) // Maximal 2 Werte im Puffer
    .collect { value ->
        delay(1500) // Langsame Verarbeitung
        println("🟢 Collected: $value")
    }
}
```
### **🔍 Ausgabe (Kapazität = 2)**
```
🔵 Emitted: 1  
🔵 Emitted: 2  (Puffer: [1, 2])  
🔵 Emitted: 3  (Puffer voll, `emit(3)` muss warten!)  
🟢 Collected: 1  
🔵 Emitted: 3  (Platz im Puffer, wird aufgenommen)  
🟢 Collected: 2  
🔵 Emitted: 4  
🟢 Collected: 3  
🔵 Emitted: 5  
🟢 Collected: 4  
🟢 Collected: 5  
```
### **🔍 Was passiert hier?**
- Die ersten **2 Werte (`1, 2`) werden sofort in den Puffer geladen**.
- Als `emit(3)` gesendet wird, ist der Puffer **voll** → `emit(3)` muss warten, bis `collect()` Platz schafft.
- Danach wird `3` in den Puffer geladen und `collect()` verarbeitet weiter.

---

## **📌 2. Unterschied zwischen `buffer(capacity = 0)` und `buffer(capacity = 10)`**
### **🔍 `buffer(0)` – Kein Puffer (Synchrones Verhalten)**
```kotlin
flow {
    for (i in 1..5) {
        emit(i)
        println("🔵 Emitted: $i")
    }
}
.buffer(capacity = 0) // Kein Puffer!
.collect { value ->
    delay(1000)
    println("🟢 Collected: $value")
}
```
**Ergebnis:**
- `emit()` **muss immer warten, bis `collect()` fertig ist**.
- **Effektiv dasselbe wie `buffer()` NICHT zu verwenden!**

---

### **🔍 `buffer(10)` – Großer Puffer**
```kotlin
flow {
    for (i in 1..10) {
        emit(i)
        println("🔵 Emitted: $i")
    }
}
.buffer(capacity = 10) // Puffer für alle Werte!
.collect { value ->
    delay(1000)
    println("🟢 Collected: $value")
}
```
**Ergebnis:**
- Alle **10 Werte werden in den Puffer geladen, bevor `collect()` startet**.
- `emit()` blockiert **erst, wenn der Puffer voll ist**.

---

## **📌 3. `buffer(capacity = Channel.UNLIMITED)` – Unendlicher Puffer**
Falls du möchtest, dass **`emit()` niemals blockiert**, kannst du `Channel.UNLIMITED` nutzen:

```kotlin
flow {
    for (i in 1..5) {
        emit(i)
        println("🔵 Emitted: $i")
    }
}
.buffer(capacity = Channel.UNLIMITED)
.collect { value ->
    delay(2000) // Sehr langsame Verarbeitung
    println("🟢 Collected: $value")
}
```
### **🔍 Was passiert hier?**
✅ **`emit()` blockiert nie, da es unendlich viele Werte speichern kann**.  
❌ **Aber Achtung:** Falls `collect()` **sehr langsam** ist, kann der Puffer unkontrolliert wachsen → **Speicherverbrauch steigt**!

---

## **📌 4. `buffer(capacity = Channel.CONFLATED)` – Überschreibt ältere Werte**
Falls dich **alte Werte nicht interessieren**, kannst du `Channel.CONFLATED` verwenden:

```kotlin
flow {
    for (i in 1..5) {
        emit(i)
        println("🔵 Emitted: $i")
    }
}
.buffer(capacity = Channel.CONFLATED) // Überschreibt ältere Werte
.collect { value ->
    delay(2000) // Sehr langsame Verarbeitung
    println("🟢 Collected: $value")
}
```
### **🔍 Verhalten**
- Falls `emit()` schneller ist als `collect()`, **werden alte Werte verworfen**.
- **`buffer()` speichert immer nur den neuesten Wert**.

**Vergleich:**

| **Puffer-Typ**  | **Blockiert `emit()`?** | **Verliert alte Werte?** |
|---------------|-----------------|-----------------|
| `buffer(3)`  | Ja, wenn voll | Nein |
| `Channel.UNLIMITED` | Nein | Nein |
| `Channel.CONFLATED` | Nein | ✅ Ja |

---

## **🚀 Fazit: Wann welchen Buffer?**
| **Szenario** | **Empfohlene `buffer()`-Option** |
|-------------|--------------------------------|
| **Standard-Puffer für asynchrones Flow** | `buffer(3)` oder `buffer(5)` |
| **Produktion ist schneller als Konsumieren** | `buffer(Channel.UNLIMITED)` |
| **Immer nur den neuesten Wert behalten** | `buffer(Channel.CONFLATED)` |
| **Synchrones Verhalten (kein Puffer)** | `buffer(0)` |

🔥 **Kurz gesagt:** `buffer(capacity)` steuert, wie viele Werte `Flow` zwischenspeichert, bevor `collect()` sie abholt. **Richtig eingesetzt, verbessert es die Performance erheblich!** 🚀
## **📌 3. Beispiel: `Flow` mit `conflate()` (Nur der neueste Wert bleibt)**
```kotlin
fun main() = runBlocking {
    flow {
        for (i in 1..5) {
            delay(500)
            emit(i)
            println("Emitted: $i")
        }
    }
    .conflate()
    .collect { value ->
        delay(1500)
        println("Collected: $value")
    }
}
```
### **🔍 Ausgabe (Gesamtdauer ~4.5s)**
```
Emitted: 1  
Emitted: 2  
Emitted: 3  
Collected: 3  (1 & 2 wurden verworfen)  
Emitted: 4  
Emitted: 5  
Collected: 5  (4 wurde verworfen)  
```
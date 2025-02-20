### **Was bedeutet "lazily gestartetes `async`" in Kotlin Coroutines?**
Standardmäßig startet `async {}` in Kotlin **sofort** eine Coroutine. Aber wenn du `start = CoroutineStart.LAZY` verwendest, wird die Coroutine **erst gestartet, wenn sie explizit mit `.start()` oder `.await()` aufgerufen wird**.

---

### **🔍 Beispiel: Standard-`async` (Sofortiger Start)**
```kotlin
import kotlinx.coroutines.*

fun main() = runBlocking {
    val job = async {
        println("Coroutine startet sofort!")
        delay(1000)
        "Ergebnis"
    }

    delay(500)
    println("Hauptprogramm läuft...")

    println("Ergebnis: ${job.await()}") // `await()` holt das Ergebnis
}
```
🔹 **Hier startet `async {}` sofort**, auch wenn wir das Ergebnis erst später brauchen.

---

### **🔍 Beispiel: `async(start = CoroutineStart.LAZY)` (Lazily gestartet)**
```kotlin
import kotlinx.coroutines.*

fun main() = runBlocking {
    val job = async(start = CoroutineStart.LAZY) {
        println("Coroutine wird erst gestartet, wenn sie gebraucht wird!")
        delay(1000)
        "Ergebnis"
    }

    delay(500)
    println("Hauptprogramm läuft...")

    println("Ergebnis: ${job.await()}") // Jetzt wird die Coroutine gestartet
}
```
🔹 **Hier startet die Coroutine nicht sofort**, sondern erst, wenn `await()` aufgerufen wird.

---

### **📝 Unterschied zwischen `DEFAULT` und `LAZY`**
| **Modus**                     | **Wann startet die Coroutine?** |
|--------------------------------|--------------------------------|
| `async {}` (Standard)         | **Sofort**, wenn `async` aufgerufen wird |
| `async(start = CoroutineStart.LAZY)` | **Erst wenn `.start()` oder `.await()` aufgerufen wird** |

---

### **🚀 Wann solltest du `CoroutineStart.LAZY` verwenden?**
✅ **Wenn du eine Coroutine nur starten willst, falls das Ergebnis wirklich gebraucht wird** (z. B. in einer Bedingung).  
✅ **Falls mehrere Coroutines existieren, die nicht unnötig starten sollen, bevor sie benötigt werden**.

**Aber Vorsicht:** Wenn du **vergisst, `await()` oder `start()` zu verwenden, wird die Coroutine nie ausgeführt!**
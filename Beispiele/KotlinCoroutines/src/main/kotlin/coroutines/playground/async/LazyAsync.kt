import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.CoroutineStart


fun main()= runBlocking {
    // async with lazy start
    val job = async(start = CoroutineStart.LAZY) {
        println("Coroutine wird erst gestartet, wenn await() aufgerufen wird")
        delay(100)
        "Ergebnis"
    }

    delay(100)
    println("Hauptprogramm läuft...")

    println("Ergebnis: ${job.await()}") // Coroutine wird erst jetzt gestartet
}
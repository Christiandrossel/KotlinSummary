package coroutines.playground.launch

import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val job: Job = launch {
        delay(1000) // Wartet 1 Sekunde
        println("Coroutine mit launch(), sofort fertig!")
    }
    println("Main läuft weiter") // Läuft sofort weiter (nicht blockiert)

    if (job.isCompleted) {
        println("Job ist fertig")
    }
    if (job.isCancelled) {
        println("Job wurde abgebrochen")
    }
    if (job.isActive) {
        println("Job ist aktiv")
    }
    job.join() // Wartet auf Job
    println("Main ist fertig")
}

class CoroutineLaunch {
}
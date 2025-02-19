package coroutines.playground.launch

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


fun main() {
    parallelCoroutines()
}

fun parallelCoroutines() = runBlocking {
    val job1 = launch {
        for (i in 1..10) {
            println("Job 1: $i")
            delay(100L)
        }
    }
    val job2 = launch {
        for (i in 1..10) {
            println("Job 2: $i")
            delay(100L)
        }
    }
    job1.join()
    job2.join()
}
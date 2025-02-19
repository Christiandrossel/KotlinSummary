package coroutines.playground.runBlocking

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main() {

}

class SimpleRunBlocking {

    fun oneRunBlocking() {
        println("Start")
        runBlocking {
            for (i in 1..10) {
                println("Im RunBlocking :D")
                delay(100)
            }
        }
        for (i in 1..10) {
            println("Im not in RunBlocking :(")
        }
        println("End")
    }



    fun runBlockingWithSuspendFunction() {
        println("Start")
        runBlocking {
            for (i in 1..10) {
                println("Im RunBlocking :D")
                delay(100)
            }
            suspendFunction()
        }
        for (i in 1..10) {
            println("Im not in RunBlocking :(")
        }
        println("End")
    }

    suspend fun suspendFunction() {
        delay(100)
        println("Im a suspend function")
    }
}
package coroutines.playground.async

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        val deffered01 = async { SimpleAsync.exampleMethods01() }
        val deffered02 = async { SimpleAsync.exampleMethods02() }

        deffered01.await()
        deffered02.await()
    }
}
object SimpleAsync {
    suspend fun exampleMethods01() {
        for (i in 1..10) {
            delay(100L)
            println("Example Method 01: $i")
        }
    }

    suspend fun exampleMethods02() {
        for (i in 1..10) {
            delay(100L)
            println("Example Method 02: $i")
        }
    }
}
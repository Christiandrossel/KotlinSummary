package coroutines.playground.async

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext


fun main() {
    runBlocking {
        val deffered01 = async { AsyncWithScopes.exampleMethods01() }
        val deffered02 = async { AsyncWithScopes.exampleMethods02() }

        deffered01.await()
        deffered02.await()
    }
}

object AsyncWithScopes {

    suspend fun exampleMethods01() = withContext(Dispatchers.IO) {
        for (i in 1..10) {
            delay(100L)
            println("Example Method 01: $i, with context: ${Dispatchers.IO}")
        }
    }

    suspend fun exampleMethods02() = withContext(Dispatchers.Default) {
        for (i in 1..10) {
            delay(100L)
            println("Example Method 02: $i, with context: ${Dispatchers.Default}")
            if (i == 5) {
                throw Exception("Error in exampleMethods02")
            }
        }
    }
}
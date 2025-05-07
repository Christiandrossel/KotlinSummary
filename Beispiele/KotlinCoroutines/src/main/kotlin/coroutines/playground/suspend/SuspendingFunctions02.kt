package coroutines.playground.suspend

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

object SuspendingFunctions02 {

    suspend fun doSomething() = coroutineScope {
        launch {
            println("Launching a coroutine")
        }
        launch { println("do something") }
    }

    suspend fun doSomethingElse() = coroutineScope {
        launch { println("do something else") }
    }

    fun main() = runBlocking {

        doSomething()
        doSomethingElse()
    }

}

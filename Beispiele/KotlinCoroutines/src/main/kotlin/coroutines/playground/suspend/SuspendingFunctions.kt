package coroutines.playground.suspend

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

suspend fun doSomething() {
    delay(100)
    println("do something")
}

suspend fun doSomethingElse() {
    delay(200)
    println("do something else")
}

fun main() = runBlocking {
    println(" Start the coroutine")

    launch { doSomething() }
    launch { doSomethingElse() }

    println("End the coroutine")
}
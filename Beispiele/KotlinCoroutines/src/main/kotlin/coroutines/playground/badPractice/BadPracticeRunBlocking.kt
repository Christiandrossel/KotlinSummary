package coroutines.playground.badPractice

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlin.system.measureTimeMillis

fun main() {
    val badPractice = BadPracticeRunBlocking()
    val time = measureTimeMillis {
        badPractice.controllerMethod01()
        badPractice.controllerMethod02()
    }
    println("Took time: $time")
}


class BadPracticeRunBlocking {

    // This is a bad practice because it blocks the main thread
    // and does not allow other coroutines to run concurrently.
    // It is better to use `launch` or `async` to run coroutines concurrently.
    // Or use `withContext` and suspend to switch to a different context.
    fun controllerMethod01() = runBlocking {
        println("Start the coroutine controller method 01")
        val doSomethingDeffered = async { doSomething() }
        val doSomethingElseDeffered = async { doSomethingElse() }
        doSomethingDeffered.await()
        doSomethingElseDeffered.await()
        println("End the coroutine controller method 01")
    }

    // This is a better practice because it uses `coroutineScope` to run
    // the coroutines concurrently without blocking the main thread.
    // It is better to use `launch` or `async` to run coroutines concurrently.
    // Or use `withContext` or `coroutineScope` and suspend to switch to a different context.
    fun controllerMethod02() = runBlocking {
        println("Start the coroutine controller method 02")
        val doSethingDeffered = async { doSomething() }
        val doSomethingElseDeffered = async { doSomethingElse() }

        doSethingDeffered.await()
        doSomethingElseDeffered.await()
        println("End the coroutine controller method 02")
    }


    suspend fun doSomething() {
        println("do something")
        delay(100)
    }

    suspend fun doSomethingElse() {
        println("do something else")
        delay(200)
    }
}
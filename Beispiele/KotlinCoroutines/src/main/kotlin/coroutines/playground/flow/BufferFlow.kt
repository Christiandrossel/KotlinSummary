package coroutines.playground.flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.flow
import kotlin.system.measureTimeMillis

fun main() {
    BufferFlow()
}

class BufferFlow {

    init {
        runBlocking {
            launch {
                val time = measureTimeMillis {
                    producer()
                        .buffer(3)
                        .collect {
                            delay(1500)
                            println("Item collected ${it.toString()}")
                        }
                }
                println("Took time: $time")
            }
        }
    }

    fun producer() = flow<Int> {
        listOf(1, 2, 3, 4, 5).forEach {
            delay(1000)
            println("Emitting item ${it.toString()}")
            emit(it)
        }
    }
}
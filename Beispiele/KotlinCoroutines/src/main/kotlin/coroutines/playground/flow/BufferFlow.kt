package coroutines.playground.flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.flow
import kotlin.system.measureTimeMillis

fun main() {
    BufferFlow()
}
class BufferFlow() {

    init {
        runBlocking {
            launch() {
                val time = measureTimeMillis {
                    producer()
                        .buffer(3)
                        .collect {
                            delay(1500)
                            println("Item collected ${it}")
                        }
                }
                println("Took time: $time")
            }
        }
    }


    fun producer() = flow<Int> {
        listOf(1, 2, 3, 4, 5).forEach {
//        delay(500)
            emit(it)
            println("Emitting item ${it.toString()}")
        }
    }
}



/**
 * Mit Buffer(3):
 * Finished
 * Emitting item 1
 * Emitting item 2
 * Emitting item 3
 * Item collected 1
 * Emitting item 4
 * Emitting item 5
 * Item collected 2
 * Item collected 3
 * Item collected 4
 * Item collected 5
 * Took time: 8114
 */

/**
 * Output: buffer():
 * Finished
 * Emitting item 1
 * Emitting item 2
 * Emitting item 3
 * Item collected 1
 * Emitting item 4
 * Emitting item 5
 * Item collected 2
 * Item collected 3
 * Item collected 4
 * Item collected 5
 * Took time: 8106
 */
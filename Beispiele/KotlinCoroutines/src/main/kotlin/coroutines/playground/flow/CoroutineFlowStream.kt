package coroutines.playground.flow

import coroutines.playground.data.EmployeeCreator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.CoroutineContext

fun main() {
//     CoroutineFlowStream()

//    SlowFlow()

    TransformFlow()
}

class CoroutineFlowStream {

    init {
        runBlocking(Dispatchers.Default) {
            employeeFlowStream().collect {
                delay(500)
                println(it)
            }
            sayHello().collect {
                println(it)
            }
        }
    }

    fun employeeFlowStream() = flow {
        val startTime = System.currentTimeMillis()
        for (i in 1..3) {
            delay(1000)
            emit("Get Employee from Database: ${System.currentTimeMillis() - startTime}")
        }
    }

    fun sayHello() = flow {
        delay(500)
        emit("Hello")
    }

    // SharedFlow
    fun sharedFlowExample() {
        val sharedFlow: SharedFlow<String> = flowOf("Hello", "World").shareIn(
            scope = CoroutineScope(Dispatchers.Default),
            started = SharingStarted.WhileSubscribed()
        )
        runBlocking {
            sharedFlow.collect {
                println(it)
            }
        }
    }

}

class SlowFlow() {
    init {
        runBlocking {
            val slowFlow = flow {
                for (i in 1..3) {
                    delay(1000) // Simuliert langsame Berechnung
                    emit(i)
                }
            }

            slowFlow.collect { value ->
                delay(1000) // Langsame Verarbeitung pro Wert
                println("Empfangen: $value")
            }
        }
    }
}


class TransformFlow() {
    init {
        runBlocking {
            val flow = employeeFlow()

            flow
                // The second character is a
                .filter { it[1] == 'a' }
                .map { it.toUpperCase() }
                .collect { value ->
                    println(value)
                }
        }
    }

    fun employeeFlow() = flow {
        val names = listOf("Hans", "Dieter", "Martin", "Marcel")
        for (name in names) {
            emit(name)
        }
    }
}
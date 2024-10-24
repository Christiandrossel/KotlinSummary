package coroutines.playground.flow

import coroutines.playground.data.EmployeeCreator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

fun main() {
    val coroutineFlowStream = CoroutineFlowStream()
}

class CoroutineFlowStream {

    init {
        runBlocking(Dispatchers.Default) {
            employeeFlowStream().collect {
                println(it)
            }
            sayHello().collect {
                println(it)
            }
        }
    }

    fun employeeFlowStream() = flow {
        kotlinx.coroutines.delay(1000)
        emit(EmployeeCreator.createEmployeeStream(10,100))
    }

    fun sayHello() = flow {
        kotlinx.coroutines.delay(500)
        emit("Hello")
    }

}
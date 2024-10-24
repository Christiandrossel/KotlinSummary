package coroutines.playground.launch

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

fun main() {
    val coroutineFlow = CoroutineFlow()
//    coroutineFlow.participantFlow().forEach {
//        println(it)
//    }

}

fun participantFlow() = flow {
    kotlinx.coroutines.delay(1000)
    emit(name)
}

val name = "Kotlin"

class CoroutineFlow {

    init {
        runBlocking {
            participantFlow().collect {
                println(it)
            }
        }
    }

    fun participantFlow() = flow {
        kotlinx.coroutines.delay(1000)
        emit(name)
    }

//    suspend fun participantAsync() = async {
//        kotlinx.coroutines.delay(1000)
//        name
//    }
}
package coroutines.flows.cold

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

val participantDatabase = listOf("Anna", "Michael", "Britta", "Frank")

//Remark: SharedFlow are hot in contrast ... advanced topic!
fun main() = collectParticipantsViaFlow()

fun collectParticipantsViaFlow() = runBlocking {
    println("Participants: ")
    val partFlow = participantFlow()
    println("Flow created...")
    delay(500)
    println("...nothing happend!")
    //Collecting launches the flow!
    partFlow.collect { println(it) }
    println("Done.")
}

fun participantFlow() = flow {
    participantDatabase.forEach {
        delay(100) // pretend we are doing something useful here
        println("Emitting $it")
        emit(it) // emit next value
    }
}


package coroutines.flows.launch

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

val participantDatabase = listOf("Anna", "Michael", "Britta", "Frank")

fun main() = collectParticipantsViaFlow()

fun collectParticipantsViaFlow() = runBlocking {
    println("Participants: ")
    participantFlow().onEach { println(it) }.collect {}
    println("Done.")
}

fun collectParticipantsViaFlowInBackground() = runBlocking {
    println("Participants: ")
    participantFlow()
        .onEach { println(it) }
        .launchIn(this)
    println("Done.")
}

fun participantFlow() = flow {
    participantDatabase.forEach {
        delay(100) // pretend we are doing something useful here
        emit(it) // emit next value
    }
}


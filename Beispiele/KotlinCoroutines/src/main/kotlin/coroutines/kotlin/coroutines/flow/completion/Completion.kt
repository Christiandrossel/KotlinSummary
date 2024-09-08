package coroutines.flows.completion

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

val participantDatabase = listOf("Anna", "Michael", "Britta", "Frank")

fun main() = collectParticipantsViaFlowWithOnCompletionActionAndExceptionHandling()

fun collectParticipantsViaFlowWithFinalAction() = runBlocking {
    println("Participants: ")
    try {
        participantFlow().collect { println(it) }
    }finally {
        println("Collection ended.")
    }
}

fun collectParticipantsViaFlowWithOnCompletionAction() = runBlocking {
    println("Participants: ")
    participantFlow()
        .onCompletion { println("Collection ended.") }
        .collect { println(it) }
}

fun collectParticipantsViaFlowWithOnCompletionActionAndExceptionHandling() = runBlocking {
    println("Participants: ")
    participantFlow()
        .onCompletion { println("Flow ended ${ if(it == null) "normally." else "with exception!"}") }
        .catch { println("An error occured: ${it.message}") }
        .collect { println(it) }
}

fun participantFlow() = flow {
    participantDatabase.forEach {
        delay(100) // pretend we are doing something useful here
        emit(it) // emit next value
        check(it.length < 5) { "BANG!" }
    }
}
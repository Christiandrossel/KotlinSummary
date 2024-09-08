package coroutines.flows.operators

import coroutines.multipleresults.doSomethingInParallel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

val participantDatabase = listOf("Anna", "Michael", "Britta", "Frank")

fun main() = collectFirstTwoParticipantsOnly()

fun collectParticipantsNameLength() = runBlocking {
    println("Participants: ")
    participantFlow()
        .onStart { println("Flow starting...") }
        .map { p -> p.length }
        .collect { println(it) }
}

fun collectFirstTwoParticipantsOnly() = runBlocking {
    println("Participants: ")
    participantFlow()
        .take(2)
        .collect { println(it) }
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
        //if(it.length>4) throw Exception("BUMM!")
        emit(it) // emit next value
    }
}
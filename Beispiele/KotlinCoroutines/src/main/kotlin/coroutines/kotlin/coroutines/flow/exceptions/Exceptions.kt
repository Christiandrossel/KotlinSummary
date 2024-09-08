package coroutines.flows.exceptions

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.lang.IllegalStateException

val participantDatabase = listOf("Anna", "Michael", "Britta", "Frank")

fun main() = collectParticipantsViaFlowExceptionWithRetry()

fun collectParticipantsViaFlowExceptionTerminatesScope() = runBlocking {
    println("Participants: ")
    launch {
        doSomethingInParallel()
    }
    participantFlow().collect { println(it) }
}

fun collectCombinedFlowExceptionTerminatesScope() = runBlocking {
    println("Participants: ")
    launch {
        doSomethingInParallel()
    }
    combineParticipantsWithPositionFlow().collect { println(it) }
}

fun collectParticipantsViaFlowExceptionWithCatch() = runBlocking {
    println("Participants: ")
    launch {
        doSomethingInParallel()
    }
    try {
        participantFlow().collect { println(it) }
    } catch(e : Exception) {
        println("Ups... '${e.message}'")
    }
}

fun collectParticipantsViaFlowExceptionWithOperator() = runBlocking {
    println("Participants: ")
    launch {
        doSomethingInParallel()
    }
    participantFlow()
        .catch{ e -> println("Ups... $e") }
        .collect { println(it) }
}

fun collectParticipantsViaFlowExceptionWithRetry() = runBlocking {
    println("Participants: ")
    launch {
        doSomethingInParallel()
    }
    participantFlow()
        .retry (2) { e -> (e is IllegalStateException).also { println("Retrying...")} }
        .catch { e -> println("Ups... $e") }
        .collect { println(it) }
}

fun combineParticipantsWithPositionFlow() =  (1..4).asFlow().zip(participantFlow()){ n, p -> "$n. $p"}.catch { e -> println("No '${e.message}' but no more data...") }

fun participantFlow() = flow {
    participantDatabase.forEach {
        delay(100) // pretend we are doing something useful here
        emit(it)
        check(it.length < 5) { "BANG!" }
    }
}

suspend fun doSomethingInParallel() {
    for (i in 1..6) {
        println("Doing stuff in parallel...")
        delay(100)
    }
}


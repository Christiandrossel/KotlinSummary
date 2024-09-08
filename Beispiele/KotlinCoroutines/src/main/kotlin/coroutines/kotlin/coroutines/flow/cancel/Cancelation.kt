package coroutines.flows.cancel

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

val participantDatabase = listOf("Anna", "Michael", "Britta", "Frank")

fun main() = collectParticipantsViaFlowWithTimeout()

fun collectParticipantsViaFlowWithTimeout() = runBlocking {
    println("Participants: ")
    withTimeout(350){
        participantFlow().collect { println(it) }
    }
}

fun collectParticipantsViaSizeLimitation() = runBlocking {
    println("Participants: ")
    participantBusyFlow().take(2).collect {
        println(it)
    }
}

fun collectParticipantsViaBusyFlowWithCancelation() = runBlocking {
    println("Participants: ")
    participantBusyFlow().collect {
        if(it.length > 4) cancel()
        println(it)
    }
}

fun collectPositionViaFlowWithTimeout() = runBlocking {
    println("Participants: ")
    withTimeout(350){
        positionFlow().collect { println(it) }
    }
}

fun collectPositionViaBusyFlowNoCancelation() = runBlocking {
    println("Participants: ")
    positionBusyFlow().collect {
        if ( it > 2) cancel()
        println(it)
    }
}

fun collectPositionViaBusyFlowCancelable() = runBlocking {
    println("Participants: ")
    positionBusyFlow()
        .cancellable()
        .collect {
        if ( it > 2) cancel()
        println(it)
    }
}

fun participantFlow() = flow {
    participantDatabase.forEach {
        delay(100) // pretend we are doing something useful here
        emit(it) // emit next value
    }
}

fun participantBusyFlow() = flow {
    participantDatabase.forEach {
        emit(it) // emit next value
    }
}

fun positionFlow() = (1..4).asFlow().onEach { delay(100) }

fun positionBusyFlow() = (1..4).asFlow()


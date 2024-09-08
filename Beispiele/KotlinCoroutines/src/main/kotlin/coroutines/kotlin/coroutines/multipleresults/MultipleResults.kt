package coroutines.multipleresults

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

val participantDatabase = listOf("Anna", "Michael", "Britta", "Frank")

fun main() = collectParticipantsViaFlow()

fun collectParticipantsFromBlockingList() = runBlocking {
    println("Participants: ")
    launch {
        doSomethingInParallel()
    }
    participantBlockingList().forEach { println(it) }
}

fun collectParticipantsFromSuspendedList() = runBlocking {
    println("Participants: ")
    val participants = async { participantSuspendList() }
    launch {
        doSomethingInParallel()
    }
   participants.await().forEach { println(it) }
}

fun collectParticipantsViaFlow() = runBlocking {
    println("Participants: ")
    launch {
        doSomethingInParallel()
    }
    participantFlow().collect { println(it) }
}

fun collectParticipantsViaFlowInParallel() = runBlocking {
    println("Participants: ")
    val partFlow = participantFlow()
    launch {
        delay(200)
        partFlow.collect{println("Inner: " + it)}
    }
    partFlow.collect{println("Outer: " + it)}
}

fun participantBlockingList() : List <String> {
    participantDatabase.forEach {
        Thread.sleep(100) // pretend we are doing something useful to get evrey entry
    }
    return participantDatabase
}

suspend fun participantSuspendList() : List <String> {
    participantDatabase.forEach {
        delay(100) // pretend we are doing something useful to get evrey entry
    }
    return participantDatabase
}

fun participantFlow() = flow {
    participantDatabase.forEach {
        delay(100) // pretend we are doing something useful here
        //println("Emitting $it")
        emit(it) // emit next value
    }
}

suspend fun doSomethingInParallel() {
    for (i in 1..6) {
        println("Doing stuff in parallel...")
        delay(100)
    }
}


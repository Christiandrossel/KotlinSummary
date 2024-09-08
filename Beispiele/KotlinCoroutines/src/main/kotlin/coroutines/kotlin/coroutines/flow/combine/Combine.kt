package coroutines.flows.combine

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

val participantDatabase = listOf("Anna", "Michael", "Britta", "Frank")
val hosts = listOf("Michaela", "Gregor", "Eva")

fun main() = mergeParticipantsAndHosts()

fun combineParticipantsWithPosition() = runBlocking {
    println("Participants: ")
    combineParticipantsWithPositionFlow().collect { println(it) }
}

fun combineParticipantsWithPositionFlow() =  (1..5).asFlow().zip(participantFlow()) {n, p -> "$n. $p"}

fun participantFlow() = flow {
    participantDatabase.forEach {
        delay(100) // pretend we are doing something useful here
        emit(it) // emit next value
    }
}

fun mergeParticipantsAndHosts() = runBlocking {
    println("Participants and Hosts: ")
    mergeParticipantsAndHostsFlow().collect { println(it) }
}

fun mergeParticipantsAndHostsFlow() = merge(participantFlow(), hostsFlow())

fun hostsFlow() = hosts.asFlow().onStart { delay(100) }.onEach { delay (10) }
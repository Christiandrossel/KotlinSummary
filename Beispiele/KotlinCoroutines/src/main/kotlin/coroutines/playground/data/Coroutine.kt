package coroutines.playground.data

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

val participant = listOf ("Anna", "Berta", "Carla", "Dora", "Ella", "Frieda", "Greta", "Hanna")

fun main () {
    participantList()
}


fun participantList() = runBlocking {
    val list = async { getParticipantAsList() }
    launch {
        somethingParallel()
    }
    list.await().forEach {
        println(it)
    }
    println("Fertig")
}

fun countParticipantAndSpeaker() = runBlocking {
    val participant = async { countParticipant() }
    val speaker = async { countSpeaker() }

    println("Mache Mittag ...")

    println(" Es sind ${participant.await()} Teilnehmer und $speaker Sprecher anwesend")

}


suspend fun countParticipant(): Int {
    delay(1500)
    return 8
}

suspend fun countSpeaker(): Int {
    delay(500)
    return 1
}

suspend fun getParticipantAsList(): List<String> {
    participant.forEach {
        delay(100)
    }
    return participant
}


suspend fun somethingParallel() {
    repeat(10) {
        delay(50)
        println("Ich arbeite...")

    }
}
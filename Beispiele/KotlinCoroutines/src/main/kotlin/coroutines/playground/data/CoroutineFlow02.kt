package coroutines.playground.data

import coroutines.printlnWithThreadInfo
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main () {
    val coroutine = CoroutineFlow02()
//    coroutine.participantFromFlow()
    coroutine.getCombinedFlow()
}

class CoroutineFlow02 {
    val speakers = listOf("Speaker1", "Speaker2", "Speaker3", "Speaker4", "Speaker5", "Speaker6", "Speaker7", "Speaker8")

    fun participantFromFlow() = runBlocking {
        val participant = getParticipantFlow() // Die Methode holt stück für Stück die werte
        launch {
            doSomethingParallel() // Währenddessen wird zusätzlich noch parallel das abgearbeitet
        }
        val speaker = getSpeakerFlow()
        printlnWithThreadInfo("Parallel work launched.")
        delay(1000)
        participant
            .onStart { printlnWithThreadInfo("Start collecting") }
            .onCompletion { printlnWithThreadInfo("Completed collecting") }
            .map { e -> e.uppercase() }
            .take(3)
            .onEach { printlnWithThreadInfo("onEach: $it") }
            .retry(3)
            .catch { e -> printlnWithThreadInfo("Caught exception: $e") }
            .launchIn(this) // Mit launchIn() wird der Flow asynchron abgearbeitet

        speaker
            .onStart { printlnWithThreadInfo("Start collecting") }
            .onCompletion { printlnWithThreadInfo("Completed collecting") }
            .map { e -> e.uppercase() }
            .take(3)
            .onEach { printlnWithThreadInfo("onEach: $it") }
            .launchIn(this) // Mit launchIn() wird der Flow asynchron abgearbeitet

        printlnWithThreadInfo("Fertig")
    }

    fun getMergedFlow() = runBlocking {
        getParticipantFlow()
            .combine(getSpeakerFlow()) { p, s -> "$p - $s" } // Mit combine() werden zwei Flows kombiniert
            .collect() { printlnWithThreadInfo(it) }
    }

    fun getCombinedFlow() = runBlocking {
        getParticipantFlow()
            .zip(getSpeakerFlow()) { p, s -> "$p - $s" } // Mit zip() werden zwei Flows kombiniert
            .collect() { printlnWithThreadInfo(it) }
    }

    fun getParticipantFlow() = flow() {
        participant.forEach {
            delay(100)
            printlnWithThreadInfo("emit $it")
            if (it.length > 4) {
                throw RuntimeException("Name too long. Name should be shorter than 5 characters.")
            }
            emit(it)
        }
    }

    fun getSpeakerFlow() = flow() {
        speakers.forEach {
            delay(100)
            printlnWithThreadInfo("emit $it")
            emit(it)
        }
    }
}


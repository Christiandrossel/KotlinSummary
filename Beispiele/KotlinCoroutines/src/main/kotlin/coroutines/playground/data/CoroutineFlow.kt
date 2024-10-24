package coroutines.playground.data

import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


val participantFlowList = listOf ("Anna", "Berta", "Carla", "Dora", "Ella", "Frieda", "Greta", "Hanna")

fun main () {
    participantFromFlow()
}


fun positionFlow() = (1..4).asFlow() // Kann nicht von Haus aus gecancelt werden

fun cancelPositonFlow() = runBlocking {
    positionFlow()
        .cancellable() // Mit cancellable() kann der Flow gecancelt werden
        .onCompletion {  } // onCompletion() wird aufgerufen, wenn der Flow beendet ist
        .catch { } // catch() wird aufgerufen, wenn ein Fehler auftritt
//        .debounce { } // debounce() wird aufgerufen, wenn der Flow zu schnell ist
        .distinctUntilChanged() // distinctUntilChanged() wird aufgerufen, wenn der Flow sich ändert
        .filter { it > 0 } // filter() wird aufgerufen, wenn der Flow gefiltert wird
        .map { it * 2 } // map() wird aufgerufen, wenn der Flow gemappt wird
        .onEach { println(it) } // onEach() wird aufgerufen, wenn der Flow durchlaufen wird
        .collect() {
            println(it)
            if (it == 2) {
                cancel()
            }
        }
}

fun participantFromFlow() = runBlocking {
    val participant = getParticipantAsFlow() // Die Methode holt stück für Stück die werte
    launch {
        doSomethingParallel() // Währenddessen wird zusätzlich noch parallel das abgearbeitet
    }
    participant.collect() {  // Mit collect() wird der Wert aus dem Flow geholt
        println(it) // hier werden die Werte ausgegeben
    } // Mit collect() wird der Thread blockiert, bis alle Werte aus dem Flow geholt wurden!!
    println("Fertig")
}
fun getParticipantAsFlow() = flow {
    participantFlowList.forEach {
        delay(100)
        emit(it)
    }
}

suspend fun doSomethingParallel() {
    repeat(10) {
        delay(50)
        println("Ich arbeite...")

    }
}
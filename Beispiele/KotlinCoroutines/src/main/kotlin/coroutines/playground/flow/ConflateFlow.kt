//package coroutines.playground.flow
//
//import kotlinx.coroutines.*
//import kotlinx.coroutines.flow.flow
//import kotlin.system.measureTimeMillis
//
//
//fun main() = runBlocking {
//    flow {
//        for (i in 1..5) {
//            delay(500) // Produziert alle 500ms
//            emit(i)
//            println("🔵 Emitted: $i")
//        }
//    }
//        .conflate() // Überspringt ältere Werte, wenn `collect()` zu langsam ist
//        .collect { value ->
//            delay(1500) // Langsame Verarbeitung (1.5s)
//            println("🟢 Collected: $value")
//        }
//    println()
//}
//
//class ConflateFlow {
//
//    init {
//        runBlocking {
//            val time = measureTimeMillis {
//                producer()
//                    .conflate()
//                    .collect {
//                        delay(300)
//                        println(it)
//                    }
//            }
//            println("Collected in $time ms")
//        }
//    }
//
//    fun producer() = flow {
//        for (i in 1..5) {
//            delay(100)
//            emit(i)
//        }
//    }
//}
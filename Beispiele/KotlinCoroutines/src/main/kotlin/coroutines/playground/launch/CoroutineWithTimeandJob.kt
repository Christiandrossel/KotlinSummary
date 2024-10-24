//package coroutines.teilnehmer.launch
//
//import coroutines.kotlin.coroutines.channels.launchWordConsumer
//import coroutines.printlnWithThreadInfo
//import kotlinx.coroutines.*
//
//fun main() = runBlocking {
//    val job = launch() {
//        try {
//            withTimeout(500) {
//                delayedSayKKON()
//            }
//        } catch (e: RuntimeException) {
//            printlnWithThreadInfo("Catched :)")  // Der Fehler wird hier abgefangen
//        }
//    }
//    val zweiterJob = launch {
//        try {
//            delay(4000)
//            printlnWithThreadInfo("und tschüss!!")
//        } catch (e: CancellationException) {
//            printlnWithThreadInfo("Kein Tschüss :(")
//        }
//    }
//    printlnWithThreadInfo("Willkommen zur")
//    job.join()
//    printlnWithThreadInfo("schön ist es hier")
//    zweiterJob.cancel()
//}
//
//
//suspend fun sayKKON() {
//    delay(1000)
//    println("KKON!")
//}
//
//
//suspend fun delayedSayKKON() = coroutineScope {
//    val outerContext = coroutineContext
//    launch {
//        try {
//            delay(2000)
//            printlnWithThreadInfo("2024")
//        } catch (e: CancellationException) {
//            printlnWithThreadInfo("Nichts wird mit 2024 :(")
//        }
//    }
//    launch(Dispatchers.IO) {
//        delay(2000)
//        printlnWithThreadInfo("digital!")
//        withContext(outerContext) {
//            printlnWithThreadInfo("digital! -  anderer Thread")
//        }
////        throw RuntimeException("Bang!!!")
//    }
//
//    printlnWithThreadInfo("KKON!")
//}
//
//
//suspend fun createList() = coroutineScope {
//    launch {
//        // create list with 10 elements from 1 to 10
//        val list = (1..10).toList()
//        list.forEach {
//            delay(1000)
//            println("list Element: $it")
//        }
//        launch {
//            delay(2000)
//            println("list Element: 11")
//            throw RuntimeException("Bang!!!")
//        }
//    }
//
//}
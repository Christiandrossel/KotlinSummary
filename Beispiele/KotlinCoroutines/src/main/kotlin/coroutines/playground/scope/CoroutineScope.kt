package coroutines.playground.scope

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    val coroutineScope = CoroutineScope()
    coroutineScope.runBlockingMain()
}

class CoroutineScope {

    // Sequentially executes doWorld followed by "Done"
    fun runBlockingMain() = runBlocking {
        doWorld()
        println("Done")
    }

    // Concurrently executes both sections
    suspend fun doWorld() = coroutineScope { // this: CoroutineScope
        launch {
            delay(2000L)
            println("World 2")
        }
        launch {
            delay(1000L)
            println("World 1")
        }
        println("Hello")
    }
}
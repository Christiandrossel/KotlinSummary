package coroutines.playground.Dispatcher

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Executors

/**
 * Where can I define the dispatcher anywhere
 */
fun main() {
    val dispatchersExample = DispatchersExample()
//    dispatchersExample.dispatchersInRunBlockingExample()
//    dispatchersExample.dispatchersInLaunchExample()
//    dispatchersExample.dispatchersComplexExample()
    dispatchersExample.dispatchersWithOwnDispatcher()
}


class DispatchersExample {

    fun dispatchersInRunBlockingExample() = runBlocking(Dispatchers.Default) {
        println("Default: I'm working in thread ${Thread.currentThread().name}")
        launch {
            println("Default: I'm working in thread ${Thread.currentThread().name}")
        }
        println("Default: I'm working in thread ${Thread.currentThread().name}")
    }

    fun dispatchersInLaunchExample() = runBlocking {
        println("Main runBlocking: I'm working in thread ${Thread.currentThread().name}")
        launch(Dispatchers.Default) {
            println("Default: I'm working in thread ${Thread.currentThread().name}")
        }
        println("Main runBlocking: I'm working in thread ${Thread.currentThread().name}")
    }


    /**
     * Output:
     * Main runBlocking: I'm working in thread DefaultDispatcher-worker-1
     * Default: I'm working in thread DefaultDispatcher-worker-2
     * Main: I'm working in thread DefaultDispatcher-worker-1
     * Main runBlocking: I'm working in thread DefaultDispatcher-worker-1
     * Default: I'm working in thread DefaultDispatcher-worker-1
     * This is all in the default dispatcher thread because the main runBlocking is in the default dispatcher
     * and the async is in the IO dispatcher
     */
    fun dispatchersComplexExample() = runBlocking(Dispatchers.Default) {
        println("Main runBlocking: I'm working in thread ${Thread.currentThread().name}")
        val defferred = async(Dispatchers.IO) {
            println("Default: I'm working in thread ${Thread.currentThread().name}")
        }
        launch(Dispatchers.Default) {
            println("Default: I'm working in thread ${Thread.currentThread().name}")
        }
        launch(Dispatchers.Unconfined) {
            println("Main: I'm working in thread ${Thread.currentThread().name}")
        }
        defferred.await()
        println("Main runBlocking: I'm working in thread ${Thread.currentThread().name}")
    }

    /**
     * With your own dispatchers
     */
    fun dispatchersWithOwnDispatcher() = runBlocking {
        val customDispatcher = Executors.newFixedThreadPool(2).asCoroutineDispatcher()
        launch(customDispatcher) {
            println("I'm working in thread ${Thread.currentThread().name}")
        }
        customDispatcher.close() // shutdown the dispatcher
        // If the dispatcher is closed, the coroutine will not be executed and the program will not terminate!
    }
}
package coroutines.playground.launch

import kotlinx.coroutines.*
import java.time.Duration
import java.time.Instant


/**
 * This method is a scheduled task, which returns all expired lends in the inventory-service.
 *
 * Scheduled call to the inventory service to return expired lends and message about lends soon to be expired.
 */
//@Scheduled(
//    timeUnit = TimeUnit.MINUTES,
//    initialDelayString = "\${scheduler.expired-lends.initial-delay-in-minutes}",
//    fixedDelayString = "\${scheduler.expired-lends.fixed-delay-in-minutes}"
//)
//@SchedulerLock(name = "ReturnExpiredLendsTask", lockAtMostFor = "\${scheduler.expired-lends.fixed-delay-in-minutes}m")
//fun scheduleReturnExpiredLendsTask() {
//    log.info("Started return expired lends task")
//    val startTime = Instant.now()
//    runBlocking(Dispatchers.IO) {
//        onleiheManagementConnector.getAllOnleiheIds().map {
//            async(executor) {
//                inventoryServiceConnector.returnExpiredLendsCall(it)
//                inventoryServiceConnector.messageAboutExpiredLendsCall(it)
//            }
//        }.awaitAll()
//    }
//    val duration = Duration.between(startTime, Instant.now())
//    log.info("Finished return expired lends task in ${duration.toHumanReadable()}")
//}

fun main() {
    val schedulingTaskCoroutineTest = SchedulingTaskCoroutineTest()
    schedulingTaskCoroutineTest.scheduledReturnExpiredLendsTask()
}

class SchedulingTaskCoroutineTest {

    val ids = listOf("12", "13", "14", "15", "16", "17", "18", "19", "20")


    suspend fun getOnleiheIds(): List<String> {
        delay(1000)
        println("Get onleihe ids")
        return ids
    }

    suspend fun returnExpiredLendsCall(id: String) {
        // create a random integer between 0,5 and 1,5
        val random = (0..5).random() + 0.5
        // wait for the random time
        delay((random * 1000).toLong())
//        Thread.sleep(1000)
        println("Return expired lends call for id: $id")
    }

    suspend fun messageAboutExpiredLendsCall(id: String) {
        // create a random integer between 0,5 and 1,5
        val random = (0..5).random() + 0.5
        // wait for the random time
        delay((random * 1000).toLong())
//        Thread.sleep(1000)
        println("Message about expired lends call for id: $id")
    }

    fun scheduledReturnExpiredLendsTask() {
        println("Started return expired lends task")
        val startTime = Instant.now()
        runBlocking(Dispatchers.IO) {
            getOnleiheIds().map {
                async {
                    returnExpiredLendsCall(it.toString())
                    messageAboutExpiredLendsCall(it.toString())
                }
            }.awaitAll()
        }
        val duration = Duration.between(startTime, Instant.now())
        println("Finished return expired lends task in $duration")
    }
}
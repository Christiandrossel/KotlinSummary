package coroutines.playground.launch

import coroutines.printlnWithThreadInfo
import coroutines.playground.data.Employee
import coroutines.playground.data.EmployeeCreator
import kotlinx.coroutines.*

fun main() = runBlocking {
    val coroutineAsync = CoroutineAsync()
    coroutineAsync.call()
}

class CoroutineAsync() {
    suspend fun call() = coroutineScope {
        val firstJob = async {
            sayFirstJob()
        }
        val secondJob = async {
            saySecondJob()
        }
        createEmployeeListAsync()
        firstJob.await()
        secondJob.await()
    }

    private suspend fun sayFirstJob() {
        delay(1000)
        println("Here come the first job")
    }

    private suspend fun saySecondJob() {
        delay(100)
        println("Here come the second job")
    }

    private fun createEmployeeListAsync() = runBlocking(Dispatchers.Default) {
        printlnWithThreadInfo("start to create employee list")
        val employeeDeferred: Deferred<List<Employee>> = async{
            delay(1000)
            EmployeeCreator.createEmployeeList(delay = 150) }
        employeeDeferred.await().forEach {
            printlnWithThreadInfo(it.toString())
        }
        printlnWithThreadInfo("finished to create employee list")
    }
}
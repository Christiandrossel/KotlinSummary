package coroutines.playground.data

import java.util.stream.Stream

object EmployeeCreator {

    fun createEmployeeList(count: Int = 10): List<Employee> {
        return (1..count).map {
            Employee("Employee $it", it * 10)
        }
    }

    suspend fun createEmployeeList(count: Int = 10, delay: Int): List<Employee> {
        return (1..count).map {
            kotlinx.coroutines.delay(delay.toLong())
            Employee("Employee $it", it * 10)
        }
    }

    suspend fun createEmployeeStream(count: Int = 10, delay: Long): Stream<Employee> {
        return (1..count).map {
            kotlinx.coroutines.delay(delay)
            Employee("Employee $it", it * 10)
        }.stream()
    }
}

data class Employee(
    val name: String,
    val age: Int
)

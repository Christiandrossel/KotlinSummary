import java.io.PrintWriter
import java.io.StringWriter
import java.time.Duration
import java.time.Instant

fun calculate() {
    val lends = 3
    val maxNumberOfLends = 3
    val reservations = 7
    val lendingPeriod = Duration.ofDays(20)
    var helper: Int = 7


    while (helper < lends) {
        helper -= lends
        println(helper)
    }

}

fun StrinBuilderExceptionTest() {
    println(Duration.ofDays(10).compareTo(Duration.ofDays(15)))

    val durationInHour = Duration.ofHours(120)
    val durationInDays = Duration.ofDays(20)
    println("Duration in Stunden: $durationInHour in Stunden ${durationInHour.toHours()}")
    println("Duration in tage: $durationInDays in Stunde: ${durationInDays.toHours()}")

    println(java.util.concurrent.TimeoutException())
    println(java.util.concurrent.TimeoutException().toString())
    println(
        StringBuilder(
            java.util.concurrent.TimeoutException().toString()
        ).append(java.util.concurrent.TimeoutException().stackTrace)
    )
}
//StrinBuilderExceptionTest()

fun divitionNull() {
    try {
        val division = 0 / 0
    } catch (e: ArithmeticException) {
        val sw = StringWriter()
        e.printStackTrace(PrintWriter(sw))
        val exceptionAsString = sw.toString()
        println(exceptionAsString)
        println(e.toString())
    }
}

fun printDivisionWithNull() {
    println(divitionNull()) //ArithemticException...
}

fun elvisOperatorTests() {
    val a = 1
    val b = 22
    a?.equals(b) ?: (b === null)

    val id: String? = null

    val result = id ?: "jd2kj3"// null ?: ObjectId().toString()
    print("result is: " + result)
}
//elvisOperatorTests()


fun minByOrNullExpressionTestWithDates() {
    val instantList = listOf( Instant.now(), Instant.EPOCH, Instant.parse("2022-05-01T08:06:43.802Z"))
    // should get instant EPOCH
    println(instantList.minOrNull()) // yes epoch is the min value
    
    // should get Instant now
    println(instantList.maxOrNull()) // yes instant now is the max value
}
minByOrNullExpressionTestWithDates()

fun copy() {
    //Method copy provided in all data classes
    data class License(val id: String, val name: String, val age: Int) {
        fun merge(other: License): License {
            other.copy(id = this.id)
        }
    }
}
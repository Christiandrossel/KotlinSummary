import java.time.Duration
import java.time.Instant
import java.time.temporal.ChronoUnit

val endDate: Instant = Instant.parse("2022-01-01T00:00:00.00Z")
val reservations = 10L
val maxActiveLends = 3
val lendingperiod: Duration = Duration.ofDays(10)

val runs = reservations.div(maxActiveLends.toDouble())
println(reservations % maxActiveLends)
println(runs)
val availabilityDate = endDate.plus(lendingperiod.multipliedBy(runs.toLong()))
println(availabilityDate)


val date = Instant.now()
println("Date:                " + date)
println("Date cut to millis:  " + date.truncatedTo(ChronoUnit.MILLIS))
println("Date cut to seconds: " + date.truncatedTo(ChronoUnit.SECONDS))
println("Date cut to hours:   " + date.truncatedTo(ChronoUnit.HOURS))


// Der Unterschied zwischen .apply und .also ist, dass .apply den Wert zurückgibt, der in der Closure zurückgegeben wird,
// während .also den ursprünglichen Wert zurückgibt.
class Person {
    var name: String = ""
    var age: Int = 0
}

// apply kann man als builder verwenden
val person = Person().apply {
    name = "John"
    age = 32
}

val person2 = Person().also {
    it.name = "John"
    it.age = 32
}

val value = person2.also { listOf<Person>(person, person) }
val value = person2.apply { listOf<Person>(person, person) }


println("apply: ${person.name}, ${person.age}")
println("also: ${person2.name}, ${person2.age}")
/*****************************************************************************************/
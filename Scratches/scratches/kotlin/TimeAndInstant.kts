import java.time.Instant

/**
 * Verhalten von Zeit
 */


// Vergleichen von Zeiten Instant
val startDate = Instant.now()
val olderDate = Instant.parse("2020-01-01T00:00:00.000Z")
val newerDate = Instant.parse("2030-01-01T00:00:00.000Z")

println(startDate > olderDate) //true
println(startDate < olderDate) //false

println(startDate > newerDate) //false
println(startDate < newerDate) //true
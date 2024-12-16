import java.time.Instant
import java.time.Period

    val period: Period = Period.ofMonths(1)
    println(period)

    val instant: Instant = Instant.now()
    println(instant)

    val instantPlusPeriod: Instant = instant + period
    println(instantPlusPeriod)

    val instantPlusPeriod02 = instant.plus(period)
    println(instantPlusPeriod02)

    val period02: Period? = null

    val instantPlusPeriod03 = instant.plus(period02)
    println(instantPlusPeriod03)



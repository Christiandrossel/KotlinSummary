
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*


fun timeZone(date: Instant = Instant.now()) {
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss", Locale.GERMANY)
    val zone = ZoneId.of("Europe/Berlin")

    val dateTime = date.atZone(zone)
    val formattedDateTime = dateTime.format(formatter)
    println(formattedDateTime)
}

// date with Instant now()
val now = Instant.now()
println(now)
timeZone(now)

// date with Instant.parse()
val date = Instant.parse("2024-01-01T00:00:00Z")
timeZone(date)

// date with a specific date
val specificDate = Instant.parse("2024-06-18T18:59:14.611Z")
timeZone(specificDate)
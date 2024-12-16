import java.time.Instant
import java.time.format.DateTimeFormatter

val date = Instant.parse("2021-01-01T00:00:00Z")

val germanformatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

val germanDate = germanformatter.format(date)


println("germanDate: $germanDate")

val englishformatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")

val englishDate = englishformatter.format(date)

println("englishDate: $englishDate")



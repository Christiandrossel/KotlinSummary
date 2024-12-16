import net.avgl.ekz.onleihe.usermessageapplication.service.DateFormatService
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

val dateFormatService = DateFormatService()

val language = "de_DE"

val date01 = Instant.parse("2024-01-01T00:00:00Z")
var formattedDate = dateFormatService.getFormattedDate(language, date01)
println(formattedDate)


val date02 = Instant.parse("2024-05-12T10:55:22Z")
formattedDate = dateFormatService.getFormattedDate(language, date02)
println(formattedDate)

val usLanguage = "en_US"
val date03 = Instant.now()
println(date03)
formattedDate = dateFormatService.getFormattedDate(usLanguage, date03)
println(formattedDate)

val ukLanguage = "en_GB"
val date05 = Instant.now()
println(date05)
formattedDate = dateFormatService.getFormattedDate(ukLanguage, date05)


val frenchLanguage = "fr_FR"
val date04 = Instant.now()
println(date04)
formattedDate = dateFormatService.getFormattedDate(frenchLanguage, date04)
println(formattedDate)

/** With format Style **/

val anotherSummerDay = Instant.parse("2024-08-01T12:30:30Z")

var defaultTimeZone = ZoneId.of("Europe/Berlin")
var fullFormat = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL).withLocale(Locale.GERMAN).withZone(defaultTimeZone)
var longFormat = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG).withLocale(Locale.GERMAN).withZone(defaultTimeZone)
var mediumFormat = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withLocale(Locale.GERMAN).withZone(defaultTimeZone)
var shortFormat = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withLocale(Locale.GERMAN).withZone(defaultTimeZone)
println(anotherSummerDay)                           // 2024-08-01T12:30:30Z
println(fullFormat.format(anotherSummerDay))        // Donnerstag, 1. August 2024, 14:30:30 Mitteleuropäische Sommerzeit
println(longFormat.format(anotherSummerDay))        // 1. August 2024, 14:30:30 MESZ
println(mediumFormat.format(anotherSummerDay))      // 01.08.2024, 14:30:30
println(shortFormat.format(anotherSummerDay))       // 01.08.24, 14:30

/** Fazit: Das LONG Format ist das beste Format für die Anzeige von Datum und Uhrzeit. **/


// Set the locale to US
fullFormat = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL).withLocale(Locale.US).withZone(defaultTimeZone)
longFormat = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG).withLocale(Locale.US).withZone(defaultTimeZone)
mediumFormat = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withLocale(Locale.US).withZone(defaultTimeZone)
shortFormat =  DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withLocale(Locale.US).withZone(defaultTimeZone)
println(anotherSummerDay)
println(fullFormat.format(anotherSummerDay))
println(longFormat.format(anotherSummerDay))
println(mediumFormat.format(anotherSummerDay))
println(shortFormat.format(anotherSummerDay))


fullFormat = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL).withLocale(Locale.UK).withZone(defaultTimeZone)
longFormat = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG).withLocale(Locale.UK).withZone(defaultTimeZone)
mediumFormat = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withLocale(Locale.UK).withZone(defaultTimeZone)
shortFormat =  DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withLocale(Locale.UK).withZone(defaultTimeZone)
println(anotherSummerDay)
println(fullFormat.format(anotherSummerDay))
println(longFormat.format(anotherSummerDay))
println(mediumFormat.format(anotherSummerDay))
println(shortFormat.format(anotherSummerDay))

fullFormat = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL).withLocale(Locale.FRANCE).withZone(defaultTimeZone)
longFormat = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG).withLocale(Locale.FRANCE).withZone(defaultTimeZone)
mediumFormat = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withLocale(Locale.FRANCE).withZone(defaultTimeZone)
shortFormat =  DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withLocale(Locale.FRANCE).withZone(defaultTimeZone)
println(anotherSummerDay)
println(fullFormat.format(anotherSummerDay))
println(longFormat.format(anotherSummerDay))
println(mediumFormat.format(anotherSummerDay))
println(shortFormat.format(anotherSummerDay))

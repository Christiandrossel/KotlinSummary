# Umgang mit java.util.Date und java.time.Instant in Kotlin

## Problemstellung

`java.util.Date` und `java.time.Instant` sind nicht direkt kompatibel. Während `Date` keine Zeitzone speichert, ist `Instant` immer in UTC. Bei der Migration von `Date` zu `Instant` kann es daher zu Problemen mit der Zeitzone kommen.

## Lösungsmöglichkeiten

### 1. Zeitzone explizit setzen
Mit `TimeZone.setDefault(TimeZone.getTimeZone("UTC"))` kann die Zeitzone für alle `Date`-Objekte auf UTC gesetzt werden. Das sorgt für konsistente Umwandlungen.

```kotlin
TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
```

### 2. Umwandlung von Date zu Instant

```kotlin
val date = Date.from(Instant.parse("2024-12-31T23:59:59Z"))
val instant = date.toInstant()
```

### 3. Date zu Mitternacht-Instant (UTC)

```kotlin
fun dateToMidnightInstant(date: Date): Instant {
    val localDate = date.toInstant().atZone(ZoneOffset.UTC).toLocalDate()
    return localDate.atStartOfDay(ZoneOffset.UTC).toInstant()
}
```

### 4. Date zu Mitternacht-Instant (Europe/Berlin)

```kotlin
fun dateToMidnightInstantInBerlin(date: Date): Instant {
    val localDate = date.toInstant().atZone(ZoneId.of("Europe/Berlin")).toLocalDate()
    return localDate.atStartOfDay(ZoneId.of("Europe/Berlin")).toInstant()
}
```

### 5. Date zu Start/Ende des Tages

```kotlin
fun dateToStartOfDayInstant(date: Date): Instant {
    val localDate = date.toInstant().atZone(ZoneOffset.UTC).toLocalDate()
    return localDate.atStartOfDay(ZoneOffset.UTC).toInstant()
}

// Ende des Tages (23:59:59)
fun dateToEndOfDayInstant(date: Date): Instant {
    val localDate = date.toInstant().atZone(ZoneOffset.UTC).toLocalDate()
    return localDate.atTime(LocalTime.MAX).toInstant(ZoneOffset.UTC)
}
```

### 6. Start und Ende von Jahr und Monat

```kotlin
fun getStartOfTheYear(): Instant {
    val today = Instant.now()
    val currentYear = today.atZone(ZoneId.systemDefault()).year
    return Instant.parse("$currentYear-01-01T00:00:00Z")
}

fun getEndOfTheYear(now: Instant): Instant {
    val year = now.atZone(ZoneId.systemDefault()).year
    return Instant.parse("$year-12-31T23:59:59Z")
}

fun getStartOfTheMonth(now: Instant): Instant {
    return LocalDate.ofInstant(now, ZoneId.systemDefault())
        .withDayOfMonth(1)
        .atStartOfDay(ZoneId.systemDefault())
        .toInstant()
}

fun getEndOfTheMonth(current: Instant): Instant {
    return LocalDate.ofInstant(current, ZoneId.systemDefault())
        .plusMonths(1)
        .atStartOfDay(ZoneId.systemDefault())
        .minusSeconds(1)
        .toInstant()
}
```

## Ergänzende Beispiele: Date und Zeitzonenformatierung

Das folgende Beispiel zeigt, wie ein Unix-Timestamp in ein Date-Objekt umgewandelt und sowohl in UTC als auch in der lokalen Zeitzone ausgegeben werden kann:

```kotlin
import java.text.SimpleDateFormat
import java.util.*

val timestamp = 1635642684600L

// Umwandlung in ein Date-Objekt
val date = Date(timestamp)

// Ausgabe in UTC
val utcFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss z").apply {
    timeZone = TimeZone.getTimeZone("UTC")
}
println("UTC-Zeit: ${utcFormatter.format(date)}")

// Ausgabe in der lokalen Zeitzone
val localFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss z").apply {
    timeZone = TimeZone.getDefault()
}
println("Lokale Zeit: ${localFormatter.format(date)}")
```

## Erweiterte Methoden für Date zu Instant (Start/Ende des Tages)

Die folgenden Methoden zeigen verschiedene Ansätze, um ein java.util.Date auf den Tagesanfang oder das Tagesende zu setzen und als Instant zu erhalten:

```kotlin
import java.time.Instant
import java.time.LocalTime
import java.time.ZoneOffset
import java.util.*

// Variante 1: Über das java.time-API
fun Date.toStartOfTheDay(): Instant {
    return this.toInstant()
        .atZone(ZoneOffset.UTC)
        .toLocalDate()
        .atStartOfDay().toInstant(ZoneOffset.UTC)
}

fun Date.toEndOfTheDay(): Instant {
    return this.toInstant()
        .atZone(ZoneOffset.UTC)
        .toLocalDate()
        .atTime(LocalTime.MAX).toInstant(ZoneOffset.UTC)
}

// Variante 2: Über Modulo-Berechnung (Epoch Millis)
fun Date.toStartOfTheDay(): Instant {
    this.time -= this.time % (24 * 60 * 60 * 1000)
    return this.toInstant()
}

fun Date.toEndOfTheDay(): Instant {
    this.time = this.time - this.time % (24 * 60 * 60 * 1000) + (23 * 60 * 60 * 1000)
    return this.toInstant()
}
```

**Hinweis:** Die Modulo-Variante verändert das Date-Objekt selbst und sollte mit Vorsicht verwendet werden!

## Hinweise
- `Date` speichert keine Zeitzone, daher ist die explizite Angabe der Zeitzone bei der Umwandlung wichtig.
- Für neue Projekte sollte bevorzugt das `java.time`-API verwendet werden.

## Beispielausgabe

```kotlin
val now = Instant.now()
println("Start of the year: ${getStartOfTheYear()}")
println("End of the year: ${getEndOfTheYear(now)}")
println("Start of the month: ${getStartOfTheMonth(now)}")
println("End of the month: ${getEndOfTheMonth(now)}")
```

---

**Tipp:** Für Zeitberechnungen und Vergleiche immer die Zeitzone beachten, um unerwartete Ergebnisse zu vermeiden.

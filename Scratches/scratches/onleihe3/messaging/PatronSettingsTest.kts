import net.avgl.ekz.onleihe.patronevents.NotificationSettings
import net.avgl.ekz.onleihe.usermessageapplication.model.NotificationChannel
import net.avgl.ekz.onleihe.usermessageapplication.model.NotificationChannel.*
import net.avgl.ekz.onleihe.usermessageapplication.model.NotificationType
import net.avgl.ekz.onleihe.usermessageapplication.model.PatronSettings

//val patronSettings = PatronSettings(
//    userId = "active-user",
//    accountName = "Active User",
//    language = "de_DE",
//    email = "notification-active-user@email.de",
//    configuration = mapOf(
//        NotificationType.LENDING_ENDS to setOf(MESSAGE, EMAIL, PUSH),
//        NotificationType.RESERVATION_AVAILABLE to setOf(MESSAGE, EMAIL, PUSH),
//    ),
//    activeNotificationChannels = setOf(MESSAGE, PUSH, EMAIL)
//)

val patronSettings = PatronSettings(
    userId = "active-user",
    accountName = "Active User",
    language = "de_DE",
    email = "notification-active-user@email.de",
    configuration = NotificationType.values()
        .associateWith { mutableSetOf(MESSAGE, EMAIL) }
        .toMutableMap(),
    activeNotificationChannels = setOf(MESSAGE, PUSH, EMAIL)
)


val topics = patronSettings.getTopicsForPushNotifications()
println(topics)

println(patronSettings.activeNotificationChannels)

var contains = patronSettings.configuration.contains(NotificationType.LENDING_ENDS)
println(contains)

patronSettings.configuration.values.forEach { println(it) }
patronSettings.configuration.keys.forEach { println(it) }
patronSettings.configuration.get(NotificationType.LENDING_ENDS)?.forEach { println(it) }
patronSettings.configuration.get(NotificationType.LENDING_ENDS)?.contains(MESSAGE)?.let { println(it) }

patronSettings.getNotificationChannel(NotificationType.LENDING_ENDS).forEach { println(it) }

patronSettings.isNotificationEnabled(MESSAGE, NotificationType.NOVELTIES).let { println(it) }

// Add Novelities to the configuration

// Test: Kann ein NotificationType mit einem Channel hinugefügt werden, indem der Channel in der Enum Klasse des Typs nicht hinterlegt ist?
patronSettings.copy(
    configuration = patronSettings.configuration + (NotificationType.NOVELTIES to setOf(MESSAGE, PUSH, EMAIL))  // ADD also PUSH what not in the original configuration
).getNotificationChannel(NotificationType.NOVELTIES).forEach { println(it) }

// JA das geht

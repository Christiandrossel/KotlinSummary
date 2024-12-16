//import com.sun.org.slf4j.internal.LoggerFactory
//import org.bson.types.ObjectId
import java.time.Duration
import java.time.Instant

enum class NotificationChannel {
    MESSAGE,
    EMAIL,
    PUSH
}

enum class NotificationType(
    private val availableForNotificationChannel: Set<NotificationChannel>
) {
    DBIB_NEWS(setOf(NotificationChannel.MESSAGE, NotificationChannel.PUSH)),
    MAINTENANCE_NEWS(setOf(NotificationChannel.MESSAGE, NotificationChannel.PUSH)),
    LIBRARY_NEWS(setOf(NotificationChannel.MESSAGE, )),
    NOVELTIES(setOf(NotificationChannel.MESSAGE, )),
    RESERVATION_AVAILABLE(setOf(NotificationChannel.MESSAGE, NotificationChannel.PUSH, NotificationChannel.EMAIL)),
    LENDING_ENDS(setOf(NotificationChannel.MESSAGE, NotificationChannel.PUSH, NotificationChannel.EMAIL)),
    NOT_USED_PRODUCT(setOf(NotificationChannel.MESSAGE, NotificationChannel.PUSH, NotificationChannel.EMAIL)),
    SOONER_AVAILABILITY(setOf(NotificationChannel.MESSAGE, ));

    fun availableFor(notificationChannel: NotificationChannel): Boolean {
        return availableForNotificationChannel.contains(notificationChannel)
    }
}


/**
 * A configuration class in which it can be specified on which channel which type of notification the user should receive.
 * @param notificationEmail The email address to which the notification should be sent.
 * @param activeNotificationChannels Includes all generally active notification channels
 * @param configuration is a set consisting of the notification and the set of channels to which the notification may be sent.
 */
data class NotificationSettings(
    val notificationEmail: String? = null,
    val activeNotificationChannels: Set<NotificationChannel>,

    val configuration: Map<NotificationType, Set<NotificationChannel>>
){
    fun isNotificationEnabled(notificationChannel: NotificationChannel, notificationType: NotificationType): Boolean {
        return notificationType.availableFor(notificationChannel) &&
                activeNotificationChannels.contains(notificationChannel) &&
                configuration[notificationType]?.contains(notificationChannel) ?: false
    }

    fun getNotificationChannel(notificationType: NotificationType): Set<NotificationChannel> {
        return NotificationChannel.values()
            .filter { activeNotificationChannels.contains(it) }
            .filter { configuration[notificationType]?.contains(it) ?: false }
            .toSet()
    }
}

fun lendExtensionPeriod(endAt: Instant) {
    val lendExtensionPeriod: ClosedRange<Instant> =  endAt.minus(Duration.ofDays(10) ?: Duration.ofDays(2))..endAt
    println(lendExtensionPeriod(Instant.parse("2022-12-19T00:00:00.00Z")))
}

fun lastNOfList() {
    //TAke the last N of the list
    val list = listOf(1)

    val lastTwo = list.takeLast(2).reversed()
    println("List: $list")
    println("The last two: $lastTwo" )
}
lastNOfList()


/** object to json **/
//fun patronSettingsToJson() {
//    val patronSettings = createPatronSettingsEntity().toDomain().toApi()
//
//    val ow = ObjectMapper().writer().withDefaultPrettyPrinter()
//    val json: String = ow.writeValueAsString(patronSettings)
//    println(json)
//}

/************************/

//data class LendItem(val id: String)
//
//data class User(val id: String)
//
//interface Event { val message: String }
//
//data class LendEvent(
//    override val message: String,
//    val lendItem: LendItem
//): Event
//
//class EventService{
//    fun send(event: Event){
//        // send message
//    }
//}
//
//open class Service(
//    val eventService: EventService
//) {
//
//    fun addLend(userId: String, lendItem: LendItem): List<LendItem> {
//        // add lend to user
//    }
//
//    fun addUser(userId: String, user: User): User {
//        // add user
//    }
//}
//
//class Proxy(val eventService: EventService): Service(eventService) {
//
//    private val log = LoggerFactory.getLogger(this::class.java)
//
//    fun <T> handleFailure(function: () -> T): T {
//        try {
//            return function()
//        } catch (e: Exception) {
//            log.warn("Failure with user with id $userId")
//            eventService.send(
//
//            )
//            throw e
//        }
//    }
//}

/***********************/

/**** Field names von einer Klasse rausholen   ****/
fun Any.getClassFieldNames(): Set<String> {
    val fields = this::class.java.declaredFields
    val fieldNames = mutableSetOf<String>()
    fields.forEach { field ->
        field.isAccessible = true
        if (field.get(this) != null) {
            fieldNames.add(field.name)
        }
    }
    return fieldNames
}

data class User(
    val name: String,
    val street: String
)

val user1 = User("Hans", "Seitenweg")

println(user1.getClassFieldNames())
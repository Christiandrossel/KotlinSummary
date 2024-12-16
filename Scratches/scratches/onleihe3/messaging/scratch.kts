import net.avgl.ekz.onleihe.usermessageapplication.model.NotificationChannel
import net.avgl.ekz.onleihe.usermessageapplication.model.NotificationType
import net.avgl.ekz.onleihe.usermessageapplication.test_data_service.service.PatronSettingsCreator
import org.apache.zookeeper.ZooDefs.OpCode.notification

// Notification

fun main() {
    val patronSettings = PatronSettingsCreator.createPatronSettings()
    val patronSettingFirst = patronSettings.first()
    val notification = patronSettingFirst.getNotificationChannel(NotificationType.RESERVATION_AVAILABLE)
    notification.forEach(::println)
}

main()



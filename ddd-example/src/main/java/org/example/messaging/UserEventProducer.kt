package org.example.messaging

import org.example.messaging.events.UserEvent
import org.example.messaging.events.UserEventType
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class UserEventProducer(
    private val kafkaTemplate: KafkaTemplate<String, Any>,
    @Value("\${app.kafka.topics.user-events}")
    private val userEventsTopic: String
) {
    private val logger = LoggerFactory.getLogger(UserEventProducer::class.java)

    fun userCreated(id: String, name: String, email: String) = send(
        UserEvent(UserEventType.CREATED, id, name, email)
    )

    fun userUpdated(id: String, name: String?, email: String?) = send(
        UserEvent(UserEventType.UPDATED, id, name, email)
    )

    fun userDeleted(id: String, name: String?, email: String?) = send(
        UserEvent(UserEventType.DELETED, id, name, email)
    )

    private fun send(event: UserEvent) {
        logger.info("Sending user event {} to topic {}", event, userEventsTopic)
        kafkaTemplate.send(userEventsTopic, event.id, event)
            .whenComplete { _, ex ->
                if (ex != null) logger.error("Failed to send user event {}", event, ex)
                else logger.debug("User event sent: {}", event)
            }
    }
}

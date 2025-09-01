package org.example.messaging.events

import java.time.Instant

data class UserEvent(
    val type: UserEventType,
    val id: String,
    val name: String?,
    val email: String?,
    val occurredAt: Instant = Instant.now()
)
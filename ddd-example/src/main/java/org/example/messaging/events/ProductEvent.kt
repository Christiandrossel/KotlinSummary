package org.example.messaging.events

import java.time.Instant

data class ProductEvent(
    val id: String,
    val name: String,
    val price: Double,
    val description: String,
    val stock: Int,
    val pictures: List<String>,
    val tags: List<String>,
    val vendor: String,
    val isAvailable: Boolean,
    val category: String,
    val eventType: String,       // z.B. "ProductCreated", "ProductUpdated"
    val occurredAt: Instant      // Zeitstempel des Events
)

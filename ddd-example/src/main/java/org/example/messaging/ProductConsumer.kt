package org.example.messaging

import org.example.messaging.events.ProductEvent
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class ProductConsumer {
    private val logger = LoggerFactory.getLogger(ProductConsumer::class.java)

    @KafkaListener(
        topics = ["\${app.kafka.topics.products}"],
        groupId = "\${spring.kafka.consumer.group-id}"
    )
    fun consumeProduct(product: ProductEvent) {
        // For minimal implementation, we log the consumed product message.
        logger.info("Consumed product message: {}", product)
        // TODO save the product to a database or something else
    }
}

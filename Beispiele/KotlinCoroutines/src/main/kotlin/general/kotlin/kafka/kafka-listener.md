# Tutorial: Kafka Listener in Spring Boot mit Kotlin

In diesem Tutorial lernst du, wie du eine Kafka Listener-Klasse in einem Spring Boot-Projekt mit Kotlin implementierst. Wir orientieren uns an der Struktur von `InventoryEventService`, verwenden aber andere Namen und erklären die relevanten Properties.

---

## 1. Properties in `application.yml` konfigurieren

Zuerst müssen die relevanten Kafka-Properties in der `application.yml` gepflegt werden. Beispiel:

```yaml
productShop:
  kafka:
    topic:
      order-events: "dev-o3-order-service"
    group:
      order-job-registry-service: "dev-order-job-registry-service"
    consumption:
      bulk-size: 100
```

- **topic.order-events**: Name des Kafka-Topics, das konsumiert werden soll.
- **group.order-job-registry-service**: Consumer Group ID.
- **consumption.bulk-size**: Anzahl der Nachrichten, die pro Poll verarbeitet werden.

---

## 2. Listener-Interface definieren

Definiere ein Interface, das die Listener-Methode vorgibt:

```kotlin
interface OrderEventConsumer {
    fun consumeOrderEvents(orderEvents: List<ConsumerRecord<String, OrderEvent>>)
}
```

---

## 3. Listener-Service implementieren

Erstelle eine Service-Klasse, die das Interface implementiert und mit `@KafkaListener` annotiert ist:

```kotlin
@Service
class OrderEventService(
    private val orderRegistry: OrderRegistry,
    @Value("productShop.kafka.consumption.bulk-size") private val bulkSize: Int
) : OrderEventConsumer {

    private val log = LoggerFactory.getLogger(this::class.java)
    private val chunkSize = 10
    private val dispatcher = Executors.newFixedThreadPool(bulkSize / chunkSize).asCoroutineDispatcher()

    @KafkaListener(
        topics = ["productShop.kafka.topic.order-events"],
        groupId = "productShop.kafka.group.order-job-registry-service",
        containerFactory = "productShopBulkContainerFactory"
    )
    override fun consumeOrderEvents(orderEvents: List<ConsumerRecord<String, OrderEvent>>) {
        orderEvents
            .chunked(chunkSize)
            .forEachParallel(dispatcher) { handleOrderEvents(it) }
    }

    private fun handleOrderEvents(chunk: List<ConsumerRecord<String, OrderEvent>>) {
        chunk.forEach {
            val orderEvent = it.value()
            log.info("Received order event (${orderEvent.javaClass.simpleName}). OrderId: ${orderEvent.orderId}")
            try {
                orderRegistry.handleOrderEvent(orderEvent)
            } catch (e: Exception) {
                log.error("Error while processing order event (${orderEvent.key})", e)
                // Fehlerbehandlung, z.B. Dead Letter Repository
            }
        }
    }
}
```

**Hinweise:**
- Die Annotation `@KafkaListener` bindet die Methode an das Topic und die Consumer Group.
- Mit `@Value` werden Properties aus der `application.yml` injiziert.
- Die Verarbeitung erfolgt parallelisiert mit Coroutines.

---

## 4. Container Factory konfigurieren

Stelle sicher, dass eine passende `ConcurrentKafkaListenerContainerFactory` Bean existiert, z.B.:

```kotlin
@Bean
fun productShopBulkContainerFactory(...): ConcurrentKafkaListenerContainerFactory<String, OrderEvent> { ... }
```

---

## 5. Zusammenfassung

- Kafka-Properties in `application.yml` pflegen
- Listener-Interface und Service-Klasse erstellen
- `@KafkaListener` verwenden und Properties injizieren
- Fehlerbehandlung und Logging implementieren

Weitere Infos: [Spring Kafka Doku](https://docs.spring.io/spring-kafka/docs/current/reference/html/)


# Tutorial: WebClient Connectoren in Spring Boot mit Kotlin

In diesem Tutorial lernst du, wie du WebClient-Connectoren in Spring Boot/Kotlin-Projekten implementierst, konfigurierst und testest. Es wird ein Beispiel für die Konfiguration, die Nutzung (anhand von ProductConnector) und das Testen eines Connectors gezeigt.

---

## 1. WebClient-Konfiguration

Lege eine zentrale Konfiguration für WebClients an, z.B. in einer eigenen Config-Klasse:

```kotlin
@Configuration
class WebClientConfig {
    @Bean
    fun productClientV2(@Value("${'$'}{connector.product.baseUrl}") baseUrl: String): WebClient =
        WebClient.builder()
            .baseUrl(baseUrl)
            .build()
}
```

In der `application.yml` pflegst du die Property:

```yaml
connector:
  product:
    baseUrl: "https://api.example.com/products"
```

---

## 2. Beispiel: ProductConnector

Ein Connector kapselt die Kommunikation mit einem externen Service. Beispielhaft am `ProductConnector`:

```kotlin
@Component
class ProductConnector(
    @Qualifier("productClientV2") private val productClient: WebClient,
    @Value("${'$'}{connector.bulkSize:25}") private val bulkSize: Int,
    private val resilience: Resilience
) {
    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java.enclosingClass)
    }

    suspend fun getProduct(productId: String, productId: String, userId: String?): ExtendedProductDetails =
        resilience.run(
            backend = Backends.INVENTORY_SERVICE
        ) {
            productClient
                .get()
                .uri {
                    it.pathSegment(productId)
                        .queryParam("productId", productId)
                        .queryParamIfPresent("userId", Optional.ofNullable(userId))
                        .build()
                }
                .retrieve()
                .onStatus(
                    { it.is4xxClientError },
                    wrapAndLogClientException(
                        logger,
                        "Failed to retrieve product $productId for product $productIdand user $userId"
                    )
                )
                .awaitBody<ExtendedProductDetails>()
        }
}
```

**Hinweise:**

- Der WebClient wird per Qualifier injiziert.
- Die Base-URL und weitere Parameter werden über Properties gesteuert.
- Fehlerbehandlung und Resilience werden explizit implementiert.

---

## 3. Testen eines WebClient-Connectors

Für Unit-Tests empfiehlt sich der Einsatz von MockWebServer (z.B. von OkHttp) oder Mockk/Mockito für den WebClient. Beispiel mit MockWebServer:

```kotlin
@SpringBootTest
class ProductConnectorTest {
    @Autowired
    lateinit var productConnector: ProductConnector

    @Autowired
    lateinit var webClientBuilder: WebClient.Builder

    private lateinit var mockWebServer: MockWebServer

    @BeforeEach
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
    }

    @AfterEach
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `should return product details`() = runBlocking {
        val responseBody = "{"id":"123","name":"Testprodukt"}"
        mockWebServer.enqueue(MockResponse().setBody(responseBody).setResponseCode(200))

        val productClient = webClientBuilder.baseUrl(mockWebServer.url("/").toString()).build()
        val connector = ProductConnector(productClient, 25, Resilience(...))

        val result = connector.getProduct("123", "product123", null)
        assertEquals("123", result.id)
        assertEquals("Testprodukt", result.name)
    }
}
```

**Wichtige Punkte:**

- Mit MockWebServer kannst du HTTP-Responses simulieren.
- Die Base-URL des WebClients wird im Test auf den MockServer gesetzt.
- So testest du den Connector isoliert und ohne echte HTTP-Calls.

---

## Fazit

- WebClient-Connectoren werden zentral konfiguriert und per DI genutzt.
- Abfragen erfolgen typischerweise mit `.get()`, `.post()`, `.retrieve()`, `.awaitBody<T>()` etc.
- Für Tests empfiehlt sich MockWebServer oder ein Mocking-Framework.

Weitere Infos: [Spring WebClient Doku](https://docs.spring.io/spring-framework/docs/current/reference/html/web-reactive.html#webflux-client)

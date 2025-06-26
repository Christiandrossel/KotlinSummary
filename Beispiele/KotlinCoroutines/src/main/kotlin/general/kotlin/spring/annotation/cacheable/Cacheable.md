# Tutorial: Caching mit @Cacheable in Spring Boot/Kotlin

Caching ist eine effektive Methode, um die Performance von Anwendungen zu verbessern und wiederholte, teure Berechnungen oder externe Aufrufe zu vermeiden. In Spring Boot kann dies einfach mit der Annotation `@Cacheable` umgesetzt werden.

---

## Warum Caching?
- **Performance:** Reduziert die Antwortzeiten, da häufig genutzte Daten nicht immer neu geladen werden müssen.
- **Entlastung externer Systeme:** Spart Ressourcen, indem z.B. externe Services oder Datenbanken weniger oft angefragt werden.
- **Skalierbarkeit:** Die Anwendung kann mehr Anfragen verarbeiten, da weniger Zeit für wiederholte Berechnungen benötigt wird.

---

## Wann sollte man @Cacheable verwenden?
- Wenn Methoden häufig mit denselben Parametern aufgerufen werden und das Ergebnis sich selten ändert.
- Bei teuren Datenbank- oder API-Abfragen, deren Ergebnis für eine gewisse Zeit gültig ist.
- Wenn die Konsistenz der Daten nicht in Echtzeit kritisch ist (z.B. Produktkatalog, Preisliste, etc.).

---

## Wie verwendet man @Cacheable?

1. **Caching aktivieren**
   In der Hauptanwendungsklasse oder einer Konfigurationsklasse:
   ```kotlin
   @EnableCaching
   @SpringBootApplication
   class ShopApplication
   ```

2. **Cache-Konfiguration**
   In der `application.yml` kann z.B. der Cache-Typ und weitere Einstellungen gesetzt werden:
   ```yaml
   spring:
     cache:
       type: caffeine # oder simple, redis, etc.
   ```

3. **Beispiel: ProductService mit @Cacheable**
   ```kotlin
   @Service
   class ProductService(
       private val productRepository: ProductRepository
   ) {
       @Cacheable("products")
       fun getProductById(productId: String): Product? {
           println("Lade Produkt aus Datenbank...")
           return productRepository.findById(productId)
       }
   }
   ```
   Beim ersten Aufruf wird das Produkt aus der Datenbank geladen und im Cache abgelegt. Weitere Aufrufe mit derselben ID kommen direkt aus dem Cache.

4. **Cache-Namen**
   Der Name (hier `"products"`) bestimmt, in welchem Cache die Daten gespeichert werden. Dieser Name kann in der Konfiguration weiter angepasst werden.

---

## Wichtige Hinweise
- **Proxy-Mechanismus:** @Cacheable funktioniert nur, wenn die Methode von außen (über den Spring-Proxy) aufgerufen wird. Interne Aufrufe innerhalb derselben Klasse umgehen den Cache!
- **Parameter:** Der Cache-Key wird standardmäßig aus den Methodenparametern gebildet. Für komplexe Keys kann das Verhalten angepasst werden.
- **Invalidierung:** Mit `@CacheEvict` können Einträge gezielt entfernt werden, z.B. nach Updates.

---

## Beispiel für einen Test

```kotlin
@SpringBootTest
class ProductServiceTest {
    @Autowired
    lateinit var productService: ProductService

    @Test
    fun `should cache product`() {
        val productId = "123"
        productService.getProductById(productId) // Lädt aus DB
        productService.getProductById(productId) // Holt aus Cache, kein DB-Zugriff mehr
    }
}
```

---

## Fazit
Mit `@Cacheable` lassen sich in Spring Boot/Kotlin Anwendungen einfach und effektiv Caching-Strategien umsetzen. Das verbessert die Performance und Skalierbarkeit, besonders bei häufig genutzten, aber selten veränderten Daten wie Produktinformationen in einem Shop.


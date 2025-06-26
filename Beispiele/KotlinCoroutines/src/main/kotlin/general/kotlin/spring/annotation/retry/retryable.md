# Tutorial: Fehlerbehandlung mit @Retryable in Spring Boot/Kotlin

Die Annotation `@Retryable` aus dem Spring Retry Modul ermöglicht es, fehlgeschlagene Methodenaufrufe automatisch zu wiederholen. Das ist besonders nützlich bei temporären Fehlern, z.B. bei Netzwerkproblemen oder instabilen externen Services.

---

## Warum @Retryable?
- **Robustheit:** Erhöht die Fehlertoleranz gegenüber temporären Ausfällen.
- **Automatisierung:** Spart Boilerplate-Code für manuelle Retry-Logik.
- **Flexibilität:** Konfigurierbare Anzahl an Versuchen, Delays und Fehlerarten.

---

## Wann sollte man @Retryable verwenden?
- Bei Aufrufen externer Systeme (z.B. REST, Datenbanken, Messaging), die gelegentlich fehlschlagen können.
- Wenn ein Fehler mit hoher Wahrscheinlichkeit durch einen erneuten Versuch behoben werden kann (z.B. Timeout, Netzwerkfehler).
- Nicht geeignet für Fehler, die durch Wiederholung nicht gelöst werden (z.B. Validierungsfehler).

---

## Wie verwendet man @Retryable?

1. **Abhängigkeit hinzufügen**
   In der `pom.xml`:
   ```xml
   <dependency>
       <groupId>org.springframework.retry</groupId>
       <artifactId>spring-retry</artifactId>
   </dependency>
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-aop</artifactId>
   </dependency>
   ```

2. **Retry aktivieren**
   In der Hauptanwendungsklasse oder einer Konfigurationsklasse:
   ```kotlin
   @EnableRetry
   @SpringBootApplication
   class ShopApplication
   ```

3. **Beispiel: ProductService mit @Retryable**
   ```kotlin
   @Service
   class ProductService(
       private val productApi: ProductApi
   ) {
       @Retryable(
           value = [RemoteServiceException::class],
           maxAttempts = 3,
           backoff = Backoff(delay = 2000)
       )
       fun fetchProduct(productId: String): Product {
           return productApi.getProduct(productId)
       }
   }
   ```
   - Die Methode wird bei `RemoteServiceException` bis zu 3x mit 2 Sekunden Abstand erneut versucht.

4. **Fehlerbehandlung mit @Recover**
   ```kotlin
   @Recover
   fun recover(e: RemoteServiceException, productId: String): Product? {
       // Fallback-Logik, z.B. Default-Produkt oder Fehler-Logging
       return null
   }
   ```
   - Diese Methode wird aufgerufen, wenn alle Versuche fehlschlagen.

---

## Hinweise
- @Retryable funktioniert nur bei Aufrufen über den Spring-Proxy (wie @Cacheable).
- Für suspend-Funktionen in Kotlin ist @Retryable nicht geeignet (siehe Decorator-Pattern-Tutorial).
- Die Retry-Parameter können auch global in der `application.yml` konfiguriert werden.

---

## Fazit
Mit `@Retryable` lassen sich in Spring Boot/Kotlin Anwendungen fehlertolerante Methoden einfach umsetzen. Das erhöht die Robustheit gegenüber temporären Fehlern, besonders bei externen Abhängigkeiten wie Produkt-APIs in einem Shop.


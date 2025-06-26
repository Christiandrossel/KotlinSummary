# Leitfaden: Resilience-Pattern ohne Annotationen mit Decorator-Pattern in Spring/Kotlin

## Einleitung
In modernen Microservice-Architekturen ist Resilienz ein zentrales Thema. Häufig werden Bibliotheken wie Resilience4j genutzt, um Circuit Breaker, Retry und andere Patterns zu implementieren. In Spring-Projekten werden diese Mechanismen oft per Annotation (@CircuitBreaker, @Retry) aktiviert. Es gibt jedoch Szenarien, in denen eine explizite, annotation-freie Implementierung Vorteile bietet – z.B. bessere Testbarkeit, mehr Kontrolle und weniger Magie.

Das Decorator-Pattern ist ein bewährtes Entwurfsmuster, um Funktionalität dynamisch zu erweitern, ohne die ursprüngliche Klasse zu verändern. In Kombination mit Resilience4j lässt sich so Resilienz explizit und flexibel implementieren.

---

## Warum sind Annotationen auf suspend-Funktionen problematisch?

Spring AOP und viele Annotation-basierte Mechanismen funktionieren nicht zuverlässig mit Kotlin `suspend`-Funktionen. Der Grund: Suspend-Funktionen werden vom Kotlin-Compiler in State-Maschinen transformiert und sind für Java-basierte Proxies und AOP-Frameworks nicht direkt interceptierbar. Das führt dazu, dass z.B. `@CircuitBreaker` oder `@Retry` auf suspend-Funktionen entweder gar nicht oder nur unzuverlässig greifen. 

**Typische Probleme:**
- Annotationen werden ignoriert, da der Proxy die Methode nicht korrekt erkennt.
- Fehlerhafte oder fehlende Fehlerbehandlung bei Ausnahmen in Coroutines.
- Unerwartetes Verhalten bei paralleler Ausführung und Suspendierung.

**Fazit:** Für Kotlin-Coroutines und suspend-Funktionen ist die Annotation-basierte Resilience-Implementierung nicht geeignet.

---

## Lösung: Explizite Anwendung per Decorator-Pattern

Durch die explizite Anwendung des Decorator-Patterns – wie im Beispiel mit der `Resilience`-Klasse – wird die Resilience-Logik direkt im Code angewendet. So werden die Probleme mit Annotationen und AOP umgangen und die volle Kontrolle über das Verhalten bei Fehlern, Retries und Circuit Breakern bleibt erhalten.

---

## Beispiel: Anwendung des Decorator-Patterns für Resilience

### 1. Zentrale Resilience-Logik kapseln
Die Klasse `Resilience` kapselt die Resilience4j-Logik und stellt Methoden bereit, um beliebige Aufrufe mit CircuitBreaker und Retry zu dekorieren:

```kotlin
@Component
class Resilience(
    private val circuitBreakerRegistry: CircuitBreakerRegistry,
    private val retryRegistry: RetryRegistry
) {
    suspend fun <T> run(backend: String, block: suspend () -> T): T { ... }
    suspend fun <T> run(backend: String, fallback: suspend (e: Throwable) -> T, block: suspend () -> T): T { ... }
    suspend fun <T> runWithRetry(backend: String, block: suspend () -> T): T { ... }
    suspend fun <T> runWithRetry(backend: String, fallback: suspend (e: Throwable) -> T, block: suspend () -> T): T { ... }
}
```

### 2. Anwendung im Connector (Decorator-Pattern)
Im `productShopManagementConnector` wird die Resilience-Logik explizit angewendet. Die Methoden werden mit der Resilience-Klasse dekoriert, anstatt Annotationen zu verwenden:

```kotlin
suspend fun getExtendedLibrary(libraryId: String): ExtendedLibrary? = resilience.run(
    backend = Backends.productShop_MANAGEMENT_SERVICE,
    fallback = {
        logger.warn("Failed to retrieve extended library $libraryId")
        null
    }
) {
    productShopClient
        .get()
        .uri("/libraries/$libraryId/extended")
        .retrieve()
        .onStatus(
            { it.is4xxClientError },
            wrapAndLogClientException(logger, "Failed to retrieve extended library $libraryId")
        )
        .awaitBody<ExtendedLibrary>()
}
```

### 3. Vorteile dieser Vorgehensweise
- **Keine Magie:** Die Resilience-Logik ist explizit und nachvollziehbar im Code sichtbar.
- **Testbarkeit:** Fallbacks und Fehlerfälle können gezielt getestet werden.
- **Flexibilität:** Unterschiedliche Resilience-Strategien können pro Methode gewählt werden.
- **Weniger Abhängigkeit von Spring AOP:** Keine versteckten Proxies oder Annotationen.
- **Suspend-freundlich:** Funktioniert zuverlässig mit Kotlin Coroutines und suspend-Funktionen.

### 4. Best Practices
- Fallback-Methoden sollten möglichst einfach und sicher sein (z.B. leere Listen, Defaults).
- Logging im Fallback hilft bei der Fehleranalyse.
- Die Konfiguration von CircuitBreaker und Retry sollte zentral erfolgen (z.B. application.yml).

---

## Fazit
Das Decorator-Pattern ermöglicht es, Resilience-Mechanismen wie CircuitBreaker und Retry explizit und flexibel in Kotlin/Spring-Projekten zu implementieren. Dies erhöht die Transparenz, Testbarkeit und Wartbarkeit des Codes – besonders in komplexen oder sicherheitskritischen Anwendungen. Gleichzeitig werden die bekannten Probleme von Annotationen auf suspend-Funktionen elegant umgangen.

**Weitere Infos:**
- [Resilience4j Dokumentation](https://resilience4j.readme.io/)
- [Decorator Pattern (Wikipedia)](https://de.wikipedia.org/wiki/Decorator)

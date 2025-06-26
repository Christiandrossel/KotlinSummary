# Spring Data Repository Tutorial

Dieses Tutorial gibt einen Überblick über die Verwendung von Spring Data Repositories am Beispiel eines MongoDB-Repositories. Es zeigt, wie eigene Repository-Interfaces erstellt, erweitert und mit benutzerdefinierten Abfragen versehen werden können.

---

## 1. Was ist ein Repository?

Ein Repository ist ein zentrales Konzept in Spring Data, das die Datenzugriffsschicht kapselt. Es bietet CRUD-Operationen (Create, Read, Update, Delete) und kann durch eigene Methoden und Abfragen erweitert werden.

---

## 2. Grundlegendes Repository-Interface

Spring Data stellt generische Interfaces wie `CrudRepository`, `JpaRepository` oder `MongoRepository` bereit. Ein eigenes Repository für eine Entität sieht z.B. so aus:

```kotlin
@Repository
interface StockHistoryRepository : MongoRepository<StockHistory, StockHistoryId>
```

- `StockHistory` ist die Entität.
- `StockHistoryId` ist der Typ des Primärschlüssels.

---

## 3. Eigene Methoden deklarieren

Spring Data erkennt Methoden anhand ihres Namens und generiert automatisch die passende Query. Beispiel:

```kotlin
fun findAllByLicenceOwnerIdAndStatusInAndStartTimeStampBeforeAndEndTimeStampAfter(
    licenceOwnerId: String,
    status: Set<StockStatus>,
    startTimeStamp: Instant,
    endTimeStamp: Instant,
): Stream<StockHistory>
```

Spring analysiert den Methodennamen und erstellt eine Query, die alle passenden Datensätze liefert.

---

## 4. Eigene Queries mit @Query

Für komplexere Abfragen kann die Annotation `@Query` verwendet werden:

```kotlin
@Query("{" +
        "'shopId': ?0," +
        "'nextStatus': {\$in: ?1}, " +
        "\$or: [{'status': {\$in: ?2}}, {\$expr: ?3}]}, " +
        "'endTimeStamp':  {\$gt: ?4, \$lt: ?5}" +
        "}")
fun findAllChangedByShopIdAndStatusChangedFromToBetweenTimestamps(
    shopId: String,
    nextStatus: Set<StockStatus>,
    statusFrom: Set<StockStatus>,
    disableNextStatusCheck: Boolean,
    fromTimestamp: Instant,
    toTimestamp: Instant,
): Stream<StockHistory>
```

Mit `@Query` kann eine MongoDB-Query direkt angegeben werden. Platzhalter wie `?0`, `?1` usw. werden durch die Methodenparameter ersetzt.

---

## 5. Repository-Erweiterungen (Custom Extension)

Für komplexe oder dynamische Abfragen kann das Repository um eine eigene Schnittstelle und Implementierung erweitert werden:

**Interface:**
```kotlin
interface StockHistoryRepositoryExtension {
    fun query(
        shopId: String,
        productIds: List<String>,
        status: Set<StockStatus>,
        fromTimestamp: Instant?,
        toTimestamp: Instant?,
    ): Stream<StockHistory>
}
```

Das Haupt-Repository erweitert dann dieses Interface:
```kotlin
interface StockHistoryRepository : MongoRepository<StockHistory, StockHistoryId>, StockHistoryRepositoryExtension
```

Die Implementierung erfolgt in einer eigenen Klasse (z.B. `StockHistoryRepositoryImpl`).

---

## 6. Beispiel: Implementierung einer Repository Extension

Die Implementierung erfolgt in einer Klasse, die das Extension-Interface implementiert und üblicherweise den Suffix `Impl` trägt. Hier ein Beispiel für die Implementierung der oben gezeigten Extension:

```kotlin
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import java.time.Instant
import java.util.stream.Stream

class StockHistoryRepositoryExtensionImpl(
    private val mongoTemplate: MongoTemplate
) : StockHistoryRepositoryExtension {
    override fun query(
        shopId: String,
        productIds: List<String>,
        status: Set<StockStatus>,
        fromTimestamp: Instant?,
        toTimestamp: Instant?,
    ): Stream<StockHistory> {
        val query = Query()
        query.addCriteria(Criteria.where("shopId").`is`(shopId))
        if (productIds.isNotEmpty()) {
            query.addCriteria(Criteria.where("productId").`in`(productIds))
        }
        if (status.isNotEmpty()) {
            val stockChangedFrom = StockStatus.entries.toSet().minus(status)
            query.addCriteria(
                Criteria().andOperator(
                    Criteria.where("nextStatus").`in`(status),
                    Criteria.where("status").`in`(stockChangedFrom)
                )
            )
        }
        if (fromTimestamp != null || toTimestamp != null) {
            val periodCriteria = Criteria.where("endTimeStamp")
            fromTimestamp?.let { periodCriteria.gt(fromTimestamp) }
            toTimestamp?.let { periodCriteria.lt(toTimestamp) }
            query.addCriteria(periodCriteria)
        }
        return mongoTemplate.stream(query, StockHistory::class.java)
    }
}
```

- Die Klasse muss im selben Package liegen wie das Repository-Interface (Spring sucht nach dem Suffix `Impl`).
- Die Implementierung nutzt `MongoTemplate` für flexible Abfragen.
- Die Methode gibt einen Stream der gefundenen Entitäten zurück.

---

## 7. Tipps
- Methoden-Namen sollten sprechend und eindeutig sein.
- Für komplexe Filter und dynamische Queries empfiehlt sich die Nutzung von `@Query` oder einer Repository-Extension.
- Die Rückgabetypen können z.B. `List`, `Stream` oder `Optional` sein.
- Für Paging und Sortierung können zusätzliche Parameter wie `Pageable` oder `Sort` verwendet werden.

---

**Weitere Infos:**
- [Spring Data MongoDB Reference](https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/)
- [Spring Data JPA Reference](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)

//package net.avgl.ekz.onleihe.mongoTemplate
//
//import com.mongodb.client.model.Filters
//import com.mongodb.client.model.Updates
//import io.mongock.api.annotations.ChangeUnit
//import io.mongock.api.annotations.Execution
//import io.mongock.api.annotations.RollbackExecution
//import net.avgl.ekz.onleihe.inventoryapplication.licence_service.database.entities.licence.SimpleLicenceEntity
//import org.slf4j.LoggerFactory
//import org.springframework.data.mongodb.core.MongoTemplate
//import org.springframework.data.mongodb.core.query.Criteria
//import org.springframework.data.mongodb.core.query.Query
//import org.springframework.data.mongodb.core.query.Update
//import org.springframework.data.mongodb.core.query.isEqualTo
//
//@Suppress("unused")
//@ChangeUnit(
//    id = "setActiveReservationsIntoComposedLicenceEntity",
//    order = "01",
//    author = "cdrossel",
//    transactional = false
//)
//class ExampleChangeLogMongock(
//    private val mongoTemplate: MongoTemplate,
//)
//private val logger = LoggerFactory.getLogger(ChangeLog001ComposedLicences::class.java)
//
///**
// * Update all ComposedLicenceEntity with activeReservations
// * find all ComposedLicenceEntity with activeReservations as stream
// * add activeReservations to composedLicenceEntity
// */
//@Execution
//fun setActiveReservationsIntoComposedLicenceEntity() {
//
//    // Create a query to find all ComposedLicenceEntity with activeReservations
//    val query = Query().addCriteria(
//        Criteria.where("_class")
//            .`is`("net.avgl.ekz.onleihe.inventoryapplication.licence_service.database.entities.licence.ComposedLicenceEntity")
//    )
//        .addCriteria(Criteria.where("activeReservations").exists(false))
//
//
//    // Stream all ComposedLicenceEntity with activeReservations
//    mongoTemplate.stream(query, Map::class.java, "licence").forEach { document ->
//        val licences = document["licences"] as List<SimpleLicenceEntity>
//
//        val activeReservations = licences
//            .map { it.activeReservations }
//            .flatten()
//            .toMutableList()
//
//        // Update the ComposedLicenceEntity with activeReservations
//        mongoTemplate.updateFirst(
//            Query().addCriteria(Criteria.where("_id").isEqualTo(document["_id"])),
//            Update().set("activeReservations", activeReservations),  // Add activeReservations to ComposedLicenceEntity
//            "licence"
//        )
//    }
//    logger.info("Migration ChangeLog001ComposedLicences executed. Added activeReservations to ComposedLicenceEntity.")
//}
//
//@RollbackExecution
//fun rollback() {
//    val collection = mongoTemplate.getCollection("licence")
//
//    val filter = Filters.and(
//        Filters.eq(
//            "_class",
//            "net.avgl.ekz.onleihe.inventoryapplication.licence_service.database.entities.licence.ComposedLicenceEntity"
//        ),
//        Filters.exists("activeReservations", true)
//    )
//
//    collection.find(filter).forEach { document ->
//        collection.updateOne(
//            Filters.eq("_id", document["_id"]),
//            Updates.unset("activeReservations")
//        )
//    }
//    logger.info("Rollback ChangeLog001ComposedLicences executed. Removed activeReservations from ComposedLicenceEntity.")
//}
//}
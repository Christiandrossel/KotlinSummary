import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import net.avgl.ekz.onleihe.ratingapplication.database.entities.UserProductId
import net.avgl.ekz.onleihe.ratingapplication.mapper.toDomain
import net.avgl.ekz.onleihe.ratingapplication.model.RatingSummary
import net.avgl.ekz.onleihe.ratingapplication.test_data_service.util.ACTIVE_USER
import net.avgl.ekz.onleihe.ratingapplication.test_data_service.util.E_BOOK_1
import net.avgl.ekz.onleihe.ratingapplication.test_data_service.util.LIBRARY_ID
import net.avgl.ekz.onleihe.ratingapplication.test_data_service.util.ONLEIHE_ID
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

// Corountine Scope

fun main() {
    val start = System.currentTimeMillis()
    runBlocking {
        async { suspendTest() }
        async { suspendTest() }
        async { suspendTest() }
    }
    val end = System.currentTimeMillis()

    println("Time taken: ${end - start}")
}

fun test() {
    Thread.sleep(1000)
}

suspend fun suspendTest() {
    withContext(Dispatchers.IO) {
        Thread.sleep(1000)
    }
}

main()
//mit runblocking(Dispatchers.IO){} und normalen test methoden: 1235ms
//mit runblocking{} und normalen test methoden: 3000ms
//mit runblocking{} und suspend methoden: 1000ms


//TEST methode
//@Test
//fun `should get a rating summary with given product id run parallel`() {
//    // Act
//    val start = System.currentTimeMillis()
//    ratingService.getRatingSummaryForProduct(ACTIVE_USER, E_BOOK_1, ONLEIHE_ID, LIBRARY_ID, 2)
//    val end = System.currentTimeMillis()
//    val durationWithCoroutine = end - start
//
//    val startWithoutCoroutine = System.currentTimeMillis()
//    getRatingSummaryForProductWithoutCoroutine(ACTIVE_USER, E_BOOK_1, ONLEIHE_ID, LIBRARY_ID, 2)
//    val endWithoutCoroutine = System.currentTimeMillis()
//    val durationWithoutCoroutine = endWithoutCoroutine - startWithoutCoroutine
//
//    //Assert
//    Assertions.assertThat(durationWithCoroutine).isLessThan(durationWithoutCoroutine)
//}
//
//private fun getRatingSummaryForProductWithoutCoroutine(userId: String, productId: String, onleiheId: String, libraryId: String, commentSize: Int): RatingSummary {
//    val userRating = userRatingRepository.findUserRatingById(
//        UserProductId(
//            userId,
//            productId
//        )
//    )?.toDomain()
//    val productRating = ratingService.getProductRating(productId, commentSize)
//    val canRate = userProductLendService.existsUserProductLend(userId, productId)
//    return RatingSummary(userRating, productRating, canRate)
//}
import java.lang.Exception


data class UserHistory(
    val productId: String
)
data class Content(
    val productId: String
)

data class Page<T> public constructor(page: Int, size: Int, totalItems: Long, totalPages: Int, val content: List<T>)

fun nullpointerAccessListTest() {
    try {
        val userHistory: Page<UserHistory>? = null
//        val distinctProductIds = userHistory.content.map { it.productId }.distinct()
        val distinctProductIds = userHistory.content.map { it.productId }.distinct()
    } catch (e: Exception) {
        println("throw Exception")
    }
}

nullpointerAccessListTest()
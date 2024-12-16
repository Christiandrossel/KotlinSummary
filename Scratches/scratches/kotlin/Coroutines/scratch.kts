    val ids = listOf("12", "13", "14", "15", "16", "17", "18", "19", "20")
fun CoroutineCheck() {
    val result = ids.map { id -> id.toInt() }
}

fun main() {
    CoroutineCheck()
}

fun getAllIds(): List<Int> {
    return ids.map { id -> id.toInt() }
}

    suspend fun returnExpiredLendsCall(id: String) {
        // create a random integer between 0,5 and 1,5
        val random = (0..5).random() + 1
        // sleep for the random time
        delay(random.toLong())
        println("Returning expired lends for id: $id")
    }

    fun messageAboutExpiredLendsCall(id: String) {
        println("Sending message about expired lends for id: $id")
    }
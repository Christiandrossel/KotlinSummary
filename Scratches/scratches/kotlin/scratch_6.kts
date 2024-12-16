//import com.fasterxml.jackson.databind.ObjectMapper
import java.time.Duration

enum class MediaType(val productFormText: String) {
    E_LEARNING("eLearning"),
    E_BOOK("eBook"),
}

data class MediaTypeRestrictions(
    val lendAcceptanceDuration: Duration,
     val maxLendingDuration: Duration
)


val mediaTypeRestrictions = mapOf(
            MediaType.E_BOOK to MediaTypeRestrictions(
                maxLendingDuration = Duration.ofDays(25),
                lendAcceptanceDuration = Duration.ofDays(6)
            )
        )

//val mapper = ObjectMapper()

/** Eine Map mit einer anderen updaten **/
val map1 = mapOf("key1" to 1, "key2" to 2, "key3" to 3)
val map2 = mutableMapOf("key2" to 20, "key3" to 30, "key4" to 40)

map2.putAll(map1)
println(map2)

/** Eine MutableMap< String, MutableSet<String>> mit einer anderen updaten **/
val mapSet1 = mutableMapOf("key1" to setOf("value1", "value2"),
    "key2" to setOf("value3", "value4")
)
val mapSet2 = mapOf("key2" to setOf("value4", "value5"),
    "key3" to setOf("value6", "value7")
)

mapSet1.putAll(mapSet2)

println(mapSet1)

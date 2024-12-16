import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "user")
@CompoundIndex(def = "{'firstName': 1, 'lastName': 1}", name = "firstLastNameIndex")
data class UserEntity(
    @Id
    val id: String? = null,

    @Indexed(unique = true)  // Index auf dem Feld, garantiert Einzigartigkeit
    val email: String,

    @Indexed              // Einfache Indexierung
    val firstName: String,

    val lastName: String,

    @Indexed               // Index für schnelles Suchen nach Geburtsdatum
    val birthDate: String
)

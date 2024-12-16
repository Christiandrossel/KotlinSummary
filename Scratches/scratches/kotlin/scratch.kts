
import io.swagger.v3.oas.annotations.Hidden
import net.avgl.ekz.onleihe.backofficeuserapi.model.BackOfficeRole
import net.avgl.ekz.onleihe.backofficeuserapi.model.OrganisationType
import net.avgl.ekz.onleihe.backofficeuserapi.model.RoleEntry
import java.time.Instant
import java.time.temporal.ChronoUnit

data class LicenceOwnerRestrictions(
    val lend: Int
)

// Basisklasse, die alle gemeinsamen Eigenschaften enthält
open class Library(
    val id: String,
    val externalId: String,
    val name: String,
    var state: String,
    var restrictions: LicenceOwnerRestrictions? = null
) {
    // Weitere Methoden oder Eigenschaften können hier hinzugefügt werden
}

// UserLibrary-Klasse, die von der Basisklasse erbt
class UserLibrary(
    id: String,
    externalId: String,
    name: String,
    state: String,
    // Die restrictions sind optional und können null sein
    restrictions: LicenceOwnerRestrictions? = null
) : Library(id, externalId, name, state, restrictions)

// CustomerLibrary-Klasse, die ebenfalls von der Basisklasse erbt
// Hier ist die restrictions-Eigenschaft obligatorisch
class CustomerLibrary(
    id: String,
    externalId: String,
    name: String,
    state: String,
    restrictions: LicenceOwnerRestrictions // Diese Eigenschaft muss initialisiert werden
) : Library(id, externalId, name, state, restrictions)

fun sortdateAscending() {
    val dates = listOf(
        Instant.now().plus(3, ChronoUnit.DAYS),
        Instant.now().plus(1, ChronoUnit.DAYS),
        Instant.now().plus(2, ChronoUnit.DAYS)
    )
    val sortedDates = dates.sorted()
    println(sortedDates)
}

//sortdateAscending()


fun sortdateDescending() {
    val dates = listOf(
        Instant.now().plus(3, ChronoUnit.DAYS),
        Instant.now().plus(1, ChronoUnit.DAYS),
        Instant.now().plus(2, ChronoUnit.DAYS)
    )
    val sortedDates = dates.sortedDescending()
    println(sortedDates)
}
//sortdateDescending()



data class BackOfficeUser(
    val id: String? = null,
    val username: String,
    var roles: Set<RoleEntry> = emptySet(),
    val email: String? = null,
    val isEmailVerified: Boolean = false,
    val firstName: String? = null,
    val lastName: String? = null
) {
    companion object {
        const val MAX_ROLES = 95
    }

    @get:Hidden
    val displayName: String
        get() = "$firstName $lastName"

    fun joinRolesToString(): String? {
        return if (!maxRolesReached()) {
            roles.joinToRolesString()
        } else null
    }

    fun joinRolesToString02(): String? {
        return if (!maxRolesReached()) {
            roles.joinToRolesString02()
        } else null
    }

    fun generateAccessControlUrl(baseUrl: String): String? {
        return if (maxRolesReached()) {
            "$baseUrl/v1/back-office/acl/users/${id}"
        } else null
    }

    private fun maxRolesReached(): Boolean {
        return roles.size > MAX_ROLES
    }

    fun Set<RoleEntry>.joinToRolesString(): String {
        return joinToString(",", "[", "]")
        { "\"${it.role.shortString}:${it.organisationId}:${it.organisationType?.shortString}\"" }
    }

    fun Set<RoleEntry>.joinToRolesString02(): String {
        return joinToString(",", "[", "]") {
            val baseString = "\"${it.role.shortString}:${it.organisationId}\""
            val orgTypeString = it.organisationType?.shortString?.let { ":$it" } ?: ""
            "$baseString$orgTypeString\""
        }
    }
}

private fun getBackOfficeUserWithRoles(): BackOfficeUser {
    return BackOfficeUser(
        id = "id",
        username = "username",
        roles = setOf(
            RoleEntry(BackOfficeRole.MANAGER, "onleihe-3", OrganisationType.ONLEIHE),
            RoleEntry(BackOfficeRole.VIEWER, "library-1", OrganisationType.LIBRARY),
            RoleEntry(BackOfficeRole.VIEWER, "library-2", OrganisationType.LIBRARY),
        ),
    )
}
val user= getBackOfficeUserWithRoles()
println(user)
println(user.joinRolesToString())
//println(getBackOfficeUserWithRoles().joinRolesToString02())
//getBackOfficeUserWithRoles().joinRolesToString02()

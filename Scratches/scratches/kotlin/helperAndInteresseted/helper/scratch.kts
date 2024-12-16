// STRING BEHAVIOR

data class RoleEntry(
    val role: BackOfficeRole,
    val organisationId: String? = null,
    var organisationType: OrganisationType? = null
)

enum class BackOfficeRole(
    val shortString: String
) {
    ADMIN("A"),
    SUPPORT("S"),
    MANAGER("M"),
    VIEWER("V");
}

enum class OrganisationType(val shortString: String) {
    ONLEIHE("O"),
    LIBRARY("L");
}

private fun Set<RoleEntry>.joinToRolesString(): String {
    return joinToString(",", "[", "]")
    { "\"${it.role.shortString}:${it.organisationId}:${it.organisationType?.shortString}\"" }
}

fun main() {
    val roles = setOf(
        RoleEntry(BackOfficeRole.ADMIN),
        RoleEntry(BackOfficeRole.MANAGER, "650410500a1fc7518488e7ea", OrganisationType.ONLEIHE)
    )
    println(roles.joinToRolesString())
    roles.joinToRolesString()
}

main()
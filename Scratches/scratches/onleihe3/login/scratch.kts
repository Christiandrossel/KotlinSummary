import com.auth0.jwt.JWT
import net.avgl.ekz.onleihe.userapplication.user_authentication_service.model.login.openid.DefaultOpenIdResponse
import net.avgl.ekz.onleihe.userapplication.user_authentication_service.model.login.openid.OpenIdResponse
import net.avgl.ekz.onleihe.userapplication.user_authentication_service.model.login.openid.astec.AstecUserinfoResponse
import net.avgl.ekz.onleihe.userapplication.user_authentication_service.model.login.openid.switch.SwitchUserinfoResponse
import net.avgl.ekz.onleihe.userapplication.user_authentication_service.model.token.OpenIdInformation
import java.time.Instant

/**
 * USername handle
 */


/**
 * @param accessToken is the token that is used after the user has been authenticated. The token can be used to access the user's information.
 * @param expiresIn in seconds indicates how long the token is valid.
 * @param idToken is the token that is used to authenticate the user. The token can be used to retrieve further information about the user.
 * @param refreshToken is the token that is used to refresh the access token.
 */
interface OpenIdResponse {
    val accessToken: String
    val expiresIn: Long
    val idToken: String
    val refreshToken: String?

    val expiresAt: Instant
        get() = Instant.now().plusSeconds(expiresIn - 10)
    val subject: String
        get() = JWT.decode(idToken).subject
    val username: String
        get() {
            val jwt = JWT.decode(idToken)
            return jwt.getClaim("email").asString()
                ?: jwt.subject
        }
    val openIdInformation: OpenIdInformation
        get() = OpenIdInformation(subject, accessToken, expiresAt, refreshToken)

}

/**
 * @param accessToken is the token that is used after the user has been authenticated. The token can be used to access the user's information.
 * @param expiresIn in seconds indicates how long the token is valid.
 * @param idToken is the token that is used to authenticate the user. The token can be used to retrieve further information about the user.
 * @param refreshToken is the token that is used to refresh the access token.
 * @param externalLibraryId is the id of the library that the user is registered in.
 * @param ageRestrictionRelease indicates the minimum age category that the user is allowed to access. The values 0, 6, 8, 12, 14, 16, 18 are possible.
 * @param userHandle is the URL that specifies a set of rights for certain resources.
 */
data class SwitchOpenIdResponse(
    override val accessToken: String,
    override val expiresIn: Long,
    override val idToken: String,
    override val refreshToken: String?,
    val externalLibraryId: String?,
    val ageRestrictionRelease: Int,
    val userHandle: String,
    override val username: String
) : OpenIdResponse {

    constructor(defaultOpenIdResponse: DefaultOpenIdResponse, switchUserinfoResponse: SwitchUserinfoResponse) : this(
        accessToken = defaultOpenIdResponse.accessToken,
        expiresIn = defaultOpenIdResponse.expiresIn,
        idToken = defaultOpenIdResponse.idToken,
        refreshToken = defaultOpenIdResponse.refreshToken,
        externalLibraryId = switchUserinfoResponse.externalLibraryId,
        ageRestrictionRelease = switchUserinfoResponse.ageRestrictionRelease,
        userHandle = switchUserinfoResponse.sub,
        username = switchUserinfoResponse.sub ?: "username"
    )
}


data class AstecOpenIdResponse(
    override val accessToken: String,
    override val expiresIn: Long,
    override val idToken: String,
    override val refreshToken: String?,
    private val usernameFromUserInfo: String?, //TODO
    val libraryUserState: Int,
    val ageRestrictionRelease: Int,
    val userHandle: String
) : OpenIdResponse {

    constructor(defaultOpenIdResponse: DefaultOpenIdResponse, astecUserinfoResponse: AstecUserinfoResponse) : this(
        accessToken = defaultOpenIdResponse.accessToken,
        expiresIn = defaultOpenIdResponse.expiresIn,
        idToken = defaultOpenIdResponse.idToken,
        refreshToken = defaultOpenIdResponse.refreshToken,
        libraryUserState = astecUserinfoResponse.libraryUserState,
        ageRestrictionRelease = astecUserinfoResponse.libraryUserAge,
        userHandle = astecUserinfoResponse.sub,
        usernameFromUserInfo = astecUserinfoResponse.username
    )

    override val username: String
        get() = usernameFromUserInfo ?: super.username

//    fun getUserName(): String {
//        if (username.isNotEmpty()) {
//            return username
//        }
//        val jwt = JWT.decode(idToken)
//        return jwt.getClaim("email").asString()
//            ?: jwt.subject
//    }

}


val switchOpenIdResponse = SwitchOpenIdResponse(
    "accessToken",
    1, "idToken",
    "refreshToken",
    "externalLibraryId",
    1,
    "userHandle",
    "username"
)
switchOpenIdResponse.username


val astecOpenIdResponse01 =
    AstecOpenIdResponse(
        "njFdajnfXN_pK8riGklwAxe6fW55ZDiI6-kGfJFK7PA",
        1,
        "eyJraWQiOiJkRFBURHZJTFpZajJLWGMwIiwiYWxnIjoiUlMyNTYifQ.eyJsaWJ1c2VyX3N0YXRlIjoiMyIsImxpYnVzZXJfYWdlIjoiMTgiLCJzdWIiOiI4Mjk1YzUwZGRkNTgyZDBmYjljZGQzYWY4NGU4MjY5MiIsImF1ZCI6InRlc3RDbGllbnQiLCJhdXRoX3RpbWUiOjE3MTM4NzI3OTUsImlzcyI6Imh0dHBzOlwvXC93d3cuYXN0ZWMuZGVcL29pZGNwIiwiZXhwIjoxNzEzODczNzIwLCJpYXQiOjE3MTM4NzI4MjAsInNpZCI6InNhbzZ2M2Fhd3lXMHZtSGFfUDR2OV9DUzJ2VnhRaWc4M3F5NmctbklWWDAifQ.HMxtoLX7rfJxbm6nV4eHW-zBtwOaNnPq_KzWI4Ja929tnFrIf79Z6Vce9aZPOR65whSoAYzemEBayET-VJzbVF2WNwiWvGuOYnnF50OxASWVX1T76IyRpRiUfm7zY5YlK9D9N6VbI1DQ2HGKAI7aL_ca2ixH-00dVeHR2Wcylr-Sz_RhWopoXrM-o6h1E2Qv59mSiBgRwwsv5bRp3cMB9xq7U-b8A6KORUipnZL-wIQcZV-47JIKXlalEQnDxFStFiRe0ve9yvTdn6OUbk7t-YvBhPa-nPTa-fsDW2lzGjcRHTW_jBCDAM0V3KhIRa_M1lk-AJhtSeC8MpEVL4clPw",
        "refreshToken",
        "Max Mustermann",
        1,
        1,
        "userHandle"
    )
println(astecOpenIdResponse01.username)

val astecOpenIdResponse02 = AstecOpenIdResponse(
    "njFdajnfXN_pK8riGklwAxe6fW55ZDiI6-kGfJFK7PA",
    1,
    "eyJraWQiOiJkRFBURHZJTFpZajJLWGMwIiwiYWxnIjoiUlMyNTYifQ.eyJsaWJ1c2VyX3N0YXRlIjoiMyIsImxpYnVzZXJfYWdlIjoiMTgiLCJzdWIiOiI4Mjk1YzUwZGRkNTgyZDBmYjljZGQzYWY4NGU4MjY5MiIsImF1ZCI6InRlc3RDbGllbnQiLCJhdXRoX3RpbWUiOjE3MTM4NzI3OTUsImlzcyI6Imh0dHBzOlwvXC93d3cuYXN0ZWMuZGVcL29pZGNwIiwiZXhwIjoxNzEzODczNzIwLCJpYXQiOjE3MTM4NzI4MjAsInNpZCI6InNhbzZ2M2Fhd3lXMHZtSGFfUDR2OV9DUzJ2VnhRaWc4M3F5NmctbklWWDAifQ.HMxtoLX7rfJxbm6nV4eHW-zBtwOaNnPq_KzWI4Ja929tnFrIf79Z6Vce9aZPOR65whSoAYzemEBayET-VJzbVF2WNwiWvGuOYnnF50OxASWVX1T76IyRpRiUfm7zY5YlK9D9N6VbI1DQ2HGKAI7aL_ca2ixH-00dVeHR2Wcylr-Sz_RhWopoXrM-o6h1E2Qv59mSiBgRwwsv5bRp3cMB9xq7U-b8A6KORUipnZL-wIQcZV-47JIKXlalEQnDxFStFiRe0ve9yvTdn6OUbk7t-YvBhPa-nPTa-fsDW2lzGjcRHTW_jBCDAM0V3KhIRa_M1lk-AJhtSeC8MpEVL4clPw",
    "refreshToken",
    null,
    1,
    1,
    "userHandle"
)
astecOpenIdResponse02.username
// is empty or null
if (astecOpenIdResponse02.username.isEmpty()) {
    println("username is empty")
} else {
    println("username is not empty")
}

println("astec openid repsonse 02: " + astecOpenIdResponse02.username)
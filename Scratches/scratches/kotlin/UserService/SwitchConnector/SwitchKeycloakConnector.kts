//package net.avgl.ekz.onleihe.userapplication.user_authentication_service.connector
//
//import com.auth0.jwt.JWT
//import com.auth0.jwt.algorithms.Algorithm
//import kotlinx.coroutines.runBlocking
//import net.avgl.ekz.onleihe.userapplication.user_authentication_service.model.Library
//import net.avgl.ekz.onleihe.userapplication.user_authentication_service.model.login.SwitchOpenIdLoginMethod
//import net.avgl.ekz.onleihe.userapplication.user_authentication_service.model.login.openid.DefaultOpenIdResponse
//import net.avgl.ekz.onleihe.userapplication.user_authentication_service.model.login.openid.OpenIdRefreshResponse
//import net.avgl.ekz.onleihe.userapplication.user_authentication_service.model.login.openid.switch.SwitchOpenIdResponse
//import net.avgl.ekz.onleihe.userapplication.user_authentication_service.model.login.openid.switch.SwitchUserinfoResponse
//import net.avgl.ekz.onleihe.userapplication.user_authentication_service.util.KeyUtil
//import org.slf4j.LoggerFactory
//import org.springframework.http.HttpHeaders
//import org.springframework.http.MediaType
//import org.springframework.stereotype.Component
//import org.springframework.util.LinkedMultiValueMap
//import org.springframework.util.MultiValueMap
//import org.springframework.web.reactive.function.BodyInserters
//import org.springframework.web.reactive.function.client.WebClient
//import org.springframework.web.reactive.function.client.awaitBody
//import java.time.Instant
//import java.util.*
//
//@Component
//class SwitchConnector(): OpenIdConnector<SwitchOpenIdLoginMethod> {
//
//    val log = LoggerFactory.getLogger(this::class.java)
//    override fun isSuitable(value: Class<*>): Boolean {
//        return value.isAssignableFrom(SwitchOpenIdLoginMethod::class.java)
//    }
//
//    /**
//     * Die auth Methode funktioniert bei Keycloak
//     *
//     */
//    override fun auth(code: String, openIdRedirectUri: String, loginMethod: SwitchOpenIdLoginMethod, library: Library): OpenIdTokenResponse {
//        return runBlocking {
//            try {
//                val webClient = webClient(loginMethod.tokenEndpoint)
//
//                val jwt = createJWT(loginMethod)
//
//                val formData: MultiValueMap<String, String> = LinkedMultiValueMap(
//                    mapOf(
//                        "response_type" to listOf("code"),
//                        "client_id" to listOf(loginMethod.clientId),
//                        "redirect_uri" to listOf(openIdRedirectUri),
//                        "scope" to listOf(loginMethod.scope), //TODO new added value
//                        "code" to listOf(code),
//                        "grant_type" to listOf("client_credentials"),
//                        "client_assertion" to listOf(jwt),
//                        "client_assertion_type" to listOf("urn:ietf:params:oauth:client-assertion-type:jwt-bearer")
//                    )
//                )
////                "https://login.eduid.ch/idp/profile/oidc/authorize?response_type=code&client_id=divibib001&redirect_uri=onleihe%3A%2F%2Fauthredirect&scope=openid+https%3A%2F%2Flogin.eduid.ch%2Fauthz%2FUser.Read&state=X9PIWhfaFIazbgkT3Ooak0eyms84o9"
//                val result = webClient.post()
//                    .body(BodyInserters.fromFormData(formData))
//                    .exchange()
//                    .block()
//                    ?.bodyToMono(String::class.java)
//                    ?.block()
//
//                /**
//                 * return access_token, expires_in, refresh_expires_in, token_type = "Bearer", id_token, not-before-policy, scope
//                 *
//                 * {"access_token":"","not-before-policy":0,"scope":"openid"}
//                 */
////             val result = webClient.post()
////                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
////                .body(BodyInserters.fromFormData(formData))
////                .retrieve()
////                .bodyToMono(String::class.java)
////                .block()
//
//                webClient.post()
//                    .body(BodyInserters.fromFormData(formData))
//                    .retrieve()
//                    .awaitBody<DefaultOpenIdTokenResponse>()//TODO statt Default... ein string im debugger
//
//
//            } catch (e: Exception) {
//                e.printStackTrace()
//                throw e
//            }
//        }
//    }
//
//    /**
//     * Diese Methode funktioniert bei Switch
//     */
//    override fun auth(
//        code: String,
//        openIdRedirectUri: String,
//        loginMethod: SwitchOpenIdLoginMethod,
//        library: Library
//    ): SwitchOpenIdResponse {
//        return runBlocking {
//            val webClient = webClient(loginMethod.tokenEndpoint)
//
//            val jwt = createJWT(loginMethod)
//
//            val formData: MultiValueMap<String, String> = LinkedMultiValueMap(
//                mapOf(
//                    "redirect_uri" to listOf(openIdRedirectUri),
//                    "code" to listOf(code),
//                    "grant_type" to listOf("authorization_code"),
//                    "client_assertion" to listOf(jwt),
//                    "client_assertion_type" to listOf("urn:ietf:params:oauth:client-assertion-type:jwt-bearer")
//                )
//            )
//
//            val openIdResponse = webClient.post()
//                .body(BodyInserters.fromFormData(formData))
//                .retrieve()
//                .awaitBody<DefaultOpenIdResponse>()
//
//            val switchUserinfoResponse = getUserinfo(openIdResponse, library)
//                .also { checkExternalLibraryId(it, library) }
//            SwitchOpenIdResponse(openIdResponse, switchUserinfoResponse)
//        }
//    }
//
//    suspend fun getUserinfo(
//        openIdTokenResponse: DefaultOpenIdResponse,
//        library: Library
//    ): SwitchUserinfoResponse {
//        return WebClient.builder()
//            .baseUrl((library.loginMethod as SwitchOpenIdLoginMethod).userinfoEndpoint) //TODO
//            .defaultHeader("Authorization", "Bearer ${openIdTokenResponse.accessToken}")
//            .build()
//            .get()
//            .retrieve()
//            .awaitBody<SwitchUserinfoResponse>()
//    }
//
//    private fun checkExternalLibraryId(switchUserinfoResponse: SwitchUserinfoResponse,  library: Library) {
//        if (switchUserinfoResponse.externalLibraryId != library.externalId) {
//            throw IllegalStateException("LibraryId from userinfo response does not match libraryId from library")
//        }
//    }
//
//
//    /**
//     * Switch has no refresh token and no refresh endpoint
//     * @throws IllegalStateException
//     */
//    override fun refresh(refreshToken: String, loginMethod: SwitchOpenIdLoginMethod): OpenIdRefreshResponse {
//        throw IllegalStateException("Switch has no refresh endpoint")
//    }
//
//    private fun webClient(url: String) = WebClient.builder()
//        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//        .baseUrl(url)
//        .build()
//
//    private  fun createJWT(loginMethod: SwitchOpenIdLoginMethod): String {
//        val privateKey = KeyUtil.readPrivateKey(loginMethod.privateKey)
//        val algorithm = Algorithm.RSA256(null, privateKey)
//        val now = Instant.now();
//
//        return JWT.create()
//            .withAudience(loginMethod.tokenEndpoint)
//            .withIssuedAt(Date.from(now))
//            .withExpiresAt(Date.from(now.plusSeconds(300)))
//            .withIssuer(loginMethod.clientId)
//            .withSubject(loginMethod.clientId)
//            .withJWTId(UUID.randomUUID().toString())
//            .sign(algorithm)
//    }
//}
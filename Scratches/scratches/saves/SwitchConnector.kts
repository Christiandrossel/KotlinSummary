package net.avgl.ekz.onleihe.userapplication.user_authentication_service.connector

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import io.github.resilience4j.retry.annotation.Retry
import kotlinx.coroutines.runBlocking
import net.avgl.ekz.onleihe.jwtsecurity.model.UserContext
import net.avgl.ekz.onleihe.userapplication.user_authentication_service.model.Library
import net.avgl.ekz.onleihe.userapplication.user_authentication_service.model.UPALoginRequest
import net.avgl.ekz.onleihe.userapplication.user_authentication_service.model.UPALoginResponse
import net.avgl.ekz.onleihe.userapplication.user_authentication_service.model.login.LoginMethod
import net.avgl.ekz.onleihe.userapplication.user_authentication_service.model.login.LoginType
import net.avgl.ekz.onleihe.userapplication.user_authentication_service.model.login.OpenIdLoginMethod
import net.avgl.ekz.onleihe.userapplication.user_authentication_service.model.login.openid.OpenIdRefreshRequest
import net.avgl.ekz.onleihe.userapplication.user_authentication_service.model.login.openid.OpenIdRefreshResponse
import net.avgl.ekz.onleihe.userapplication.user_authentication_service.model.login.openid.OpenIdTokenRequest
import net.avgl.ekz.onleihe.userapplication.user_authentication_service.model.login.openid.OpenIdTokenResponse
import net.avgl.ekz.onleihe.userapplication.user_authentication_service.service.PatronContextService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import java.util.*

@Component
class SwitchConnector(
    @Autowired private val patronContextService: PatronContextService,
): OAuth2Connector {

    override fun isSuitable(loginMethod: LoginMethod): Boolean {
        return loginMethod.loginType == LoginType.SWITCH
    }


    override fun auth(code: String, openIdRedirectUri: String, loginMethod: OpenIdLoginMethod, library: Library): UserContext {
        TODO("Not yet implemented")
    }

    override fun refresh(refreshToken: String, loginMethod: OpenIdLoginMethod): OpenIdRefreshResponse {
        return runBlocking {
            val webClient = webClient(loginMethod.tokenEndpoint)

            val openIdRefreshRequest = OpenIdRefreshRequest(
                loginMethod.clientId,
                loginMethod.clientSecret,
                refreshToken
            )

            webClient.post()
                .body(BodyInserters.fromFormData(openIdRefreshRequest.asFormData()))
                .retrieve()
                .awaitBody()
        }
    }

    private fun webClient(url: String) = WebClient.builder()
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
        .baseUrl(url)
        .build()
}
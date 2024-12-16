package net.avgl.ekz.onleihe.userapplication.user_authentication_service.service

import net.avgl.ekz.onleihe.jwtsecurity.model.UserContext
import net.avgl.ekz.onleihe.userapplication.user_authentication_service.connector.OAuth2Connector
import net.avgl.ekz.onleihe.userapplication.user_authentication_service.connector.OpenIdConnector
import net.avgl.ekz.onleihe.userapplication.user_authentication_service.model.LoginInformation
import net.avgl.ekz.onleihe.userapplication.user_authentication_service.model.login.OpenIdLoginMethod
import org.springframework.stereotype.Service

@Service
class OpenIdService(
    private val connectors: List<OAuth2Connector>,
    private val patronContextService: PatronContextService,
    private val libraryService: LibraryService
) {


    fun login(openIdCode: String, openIdRedirectUri: String, libraryId: String): UserContext {
        val library = libraryService.get(libraryId)
        val loginMethod = libraryService.getLoginMethod(libraryId) as OpenIdLoginMethod
        val connector = connectors.single { it.isSuitable(loginMethod) }
        return connector.auth(openIdCode, openIdRedirectUri, loginMethod, library)
    }

    fun isValid(openIdRefreshToken: String, libraryId: String): Boolean {
        val loginMethod = libraryService.getLoginMethod(libraryId) as OpenIdLoginMethod

        return try {
            val connector = connectors.single { it.isSuitable(loginMethod) }
            val openIdRefreshResponse =connector.refresh(openIdRefreshToken, loginMethod)
            openIdRefreshResponse.isValid()
        } catch (ignored: Exception) {
            false
        }
    }
}

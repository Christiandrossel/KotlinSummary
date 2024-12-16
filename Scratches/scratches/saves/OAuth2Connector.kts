package net.avgl.ekz.onleihe.userapplication.user_authentication_service.connector

import net.avgl.ekz.onleihe.jwtsecurity.model.UserContext
import net.avgl.ekz.onleihe.userapplication.user_authentication_service.model.Library
import net.avgl.ekz.onleihe.userapplication.user_authentication_service.model.login.LoginMethod
import net.avgl.ekz.onleihe.userapplication.user_authentication_service.model.login.OpenIdLoginMethod
import net.avgl.ekz.onleihe.userapplication.user_authentication_service.model.login.openid.OpenIdRefreshResponse

interface OAuth2Connector {
    fun isSuitable(loginMethod: LoginMethod): Boolean

    fun auth(code: String, openIdRedirectUri: String, loginMethod: OpenIdLoginMethod, library: Library): UserContext

    fun refresh(refreshToken: String, loginMethod: OpenIdLoginMethod): OpenIdRefreshResponse
}
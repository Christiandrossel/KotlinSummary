package org.example.openid

import org.example.openid.models.AuthResult

/**
 * Defines a generic authentication connector that can perform a login against an OpenID/OAuth provider.
 */
fun interface Connector {
    /**
     * Attempts to authenticate a user and returns an [AuthResult].
     */
    fun login(): AuthResult
}

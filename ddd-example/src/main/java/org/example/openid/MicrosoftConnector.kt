package org.example.openid

import org.example.openid.models.AuthResult
import org.example.openid.models.UserProfile

/**
 * A simple Microsoft (Azure AD) authentication connector.
 * Simulates validating an ID token from Microsoft identity platform.
 */
class MicrosoftConnector(
    private val idTokenProvider: () -> String?
) : Connector {

    override fun login(): AuthResult {
        return try {
            val token = idTokenProvider()
                ?: return AuthResult.Error("Missing Microsoft ID token")

            if (token.isBlank()) {
                return AuthResult.Error("Microsoft ID token is blank")
            }

            val claims: Map<String, Any?> = mapOf(
                "iss" to "https://login.microsoftonline.com/{tenantid}/v2.0",
                "provider" to "microsoft",
                "tid" to "sample-tenant-id"
            )

            val profile = UserProfile(
                name = "Microsoft User",
                email = "azure.user@example.com",
                claims = claims
            )
            AuthResult.Success(profile)
        } catch (ex: Exception) {
            AuthResult.Error("Microsoft login failed: ${ex.message}", ex)
        }
    }
}

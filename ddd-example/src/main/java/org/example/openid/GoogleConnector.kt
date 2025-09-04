package org.example.openid

import org.example.openid.models.AuthResult
import org.example.openid.models.UserProfile

/**
 * A simple Google authentication connector. In a real application this would
 * validate an ID token against Google's token verification endpoint or rely on
 * Spring Security's OIDC support. Here we simulate the behavior and prepare for
 * integration by accepting a token provider.
 */
class GoogleConnector(
    private val idTokenProvider: () -> String?
) : Connector {

    override fun login(): AuthResult {
        return try {
            val token = idTokenProvider()
                ?: return AuthResult.Error("Missing Google ID token")

            // Simulate verification
            if (token.isBlank()) {
                return AuthResult.Error("Google ID token is blank")
            }

            // In real usage, decode/verify token and build claims
            val claims: Map<String, Any?> = mapOf(
                "iss" to "https://accounts.google.com", //for example
                "aud" to "your-client-id.apps.googleusercontent.com",
                "sub" to "1234567890",
                "email_verified" to true
            )

            val profile = UserProfile(
                name = "Google User",
                email = "user@example.com",
                claims = claims
            )
            AuthResult.Success(profile)
        } catch (ex: Exception) {
            AuthResult.Error("Google login failed: ${ex.message}", ex)
        }
    }
}

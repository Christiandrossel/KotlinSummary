package org.example.openid

import org.example.openid.models.AuthResult
import org.example.openid.models.UserProfile

/**
 * A simple GitHub authentication connector.
 * Simulates exchanging/validating an OAuth access token obtained from GitHub.
 */
class GitHubConnector(
    private val accessTokenProvider: () -> String?
) : Connector {

    override fun login(): AuthResult {
        return try {
            val token = accessTokenProvider()
                ?: return AuthResult.Error("Missing GitHub access token")

            if (token.isBlank()) {
                return AuthResult.Error("GitHub access token is blank")
            }

            // Simulated user info retrieval from GitHub API
            val claims: Map<String, Any?> = mapOf(
                "iss" to "https://github.com",
                "provider" to "github",
                "scope" to listOf("read:user", "user:email")
            )

            val profile = UserProfile(
                name = "GitHub User",
                email = "octo.user@example.com",
                claims = claims
            )
            AuthResult.Success(profile)
        } catch (ex: Exception) {
            AuthResult.Error("GitHub login failed: ${ex.message}", ex)
        }
    }
}

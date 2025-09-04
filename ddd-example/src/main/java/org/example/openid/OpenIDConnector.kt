package org.example.openid

import org.example.openid.models.AuthResult

/**
 * Coordinates authentication by delegating to a specific [Connector] and
 * handling cross-cutting error handling concerns.
 *
 * Acts as a Strategy context: it can select a concrete [Connector] (strategy)
 * by provider key and execute it.
 */
class OpenIDConnector(
    private val strategies: Map<String, () -> Connector> = emptyMap()
) {

    /**
     * Uses the provided [connector] to authenticate. Ensures any unexpected
     * exception is captured and returned as [AuthResult.Error].
     */
    fun authenticate(connector: Connector): AuthResult {
        return try {
            when (val result = connector.login()) {
                is AuthResult.Success -> result
                is AuthResult.Error -> result
            }
        } catch (ex: Exception) {
            AuthResult.Error("Authentication failed: ${ex.message}", ex)
        }
    }

    /**
     * Resolves a strategy by [provider] (e.g. "google", "github", "microsoft") and authenticates.
     */
    fun authenticate(provider: String): AuthResult {
        val key = provider.lowercase()
        val connectorFactory = strategies[key]
            ?: return AuthResult.Error("Unknown provider: $provider")
        return authenticate(connectorFactory.invoke())
    }
}

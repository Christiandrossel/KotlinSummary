package org.example.openid.models

/**
 * Minimal user profile information returned on successful authentication.
 */
data class UserProfile(
    val name: String?,
    val email: String?,
    val claims: Map<String, Any?> = emptyMap()
)
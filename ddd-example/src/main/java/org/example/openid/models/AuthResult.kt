package org.example.openid.models

/**
 * Canonical result type for authentication attempts.
 */
sealed class AuthResult {
    data class Success(val user: UserProfile) : AuthResult()
    data class Error(val message: String, val cause: Throwable? = null) : AuthResult()
}
//package org.example.openid
//
///**
// * Canonical result type for authentication attempts.
// */
//sealed class AuthResult {
//    data class Success(val user: UserProfile) : AuthResult()
//    data class Error(val message: String, val cause: Throwable? = null) : AuthResult()
//}
//
///**
// * Minimal user profile information returned on successful authentication.
// */
//data class UserProfile(
//    val name: String?,
//    val email: String?,
//    val claims: Map<String, Any?> = emptyMap()
//)

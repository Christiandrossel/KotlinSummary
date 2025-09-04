package org.example.controler

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

/**
 * Backend-initiated login entrypoint. The frontend calls /auth/login and the backend
 * redirects to Spring Security's OAuth2 authorization endpoint for the desired provider.
 * After user authenticates with the provider, Spring Security handles the callback, exchanges
 * the code for tokens, and establishes the HTTP session. The frontend never sees tokens.
 */
@RestController
class LoginController {

    @GetMapping("/auth/login")
    fun login(): ResponseEntity<Void> {
        // Default to Google for backwards-compatibility
        return ResponseEntity.status(HttpStatus.FOUND)
            .header("Location", "/oauth2/authorization/google")
            .build()
    }

    @GetMapping("/auth/login/{provider}")
    fun loginWithProvider(@PathVariable provider: String): ResponseEntity<Void> {
        // Redirect to Spring Security's client registration id
        return ResponseEntity.status(HttpStatus.FOUND)
            .header("Location", "/oauth2/authorization/${provider}")
            .build()
    }
}

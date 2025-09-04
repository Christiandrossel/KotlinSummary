package org.example.controler

import org.example.openid.OpenIDConnector
import org.example.openid.models.AuthResult
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class ProviderAuthController(
    private val openIDConnector: OpenIDConnector
) {

    @GetMapping("/auth/connector/{provider}")
    fun authenticateWithProvider(@PathVariable provider: String): ResponseEntity<Any> {
        return when (val result = openIDConnector.authenticate(provider)) {
            is AuthResult.Success -> {
                ResponseEntity.ok(
                    mapOf(
                        "authenticated" to true,
                        "provider" to provider.lowercase(),
                        "user" to mapOf(
                            "name" to result.user.name,
                            "email" to result.user.email,
                            "claims" to result.user.claims
                        )
                    )
                )
            }
            is AuthResult.Error -> {
                ResponseEntity.badRequest().body(
                    mapOf(
                        "authenticated" to false,
                        "provider" to provider.lowercase(),
                        "error" to result.message
                    )
                )
            }
        }
    }
}

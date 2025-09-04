package org.example.controler

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController {

    @GetMapping("/me")
    fun me(@AuthenticationPrincipal user: OidcUser?): Map<String, Any?> {
        return if (user != null) {
            mapOf(
                "authenticated" to true,
                "name" to user.fullName,
                "email" to user.email,
                "claims" to user.claims
            )
        } else {
            mapOf("authenticated" to false)
        }
    }
}

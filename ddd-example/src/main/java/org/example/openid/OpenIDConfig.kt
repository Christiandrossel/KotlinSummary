package org.example.openid

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenIDConfig {

    @Bean
    fun openIDConnector(): OpenIDConnector {
        // For demonstration purposes, these providers return a non-blank placeholder token.
        val strategies: Map<String, () -> Connector> = mapOf(
            "google" to { GoogleConnector { "session-google-token" } },
            "github" to { GitHubConnector { "session-github-token" } },
            "microsoft" to { MicrosoftConnector { "session-microsoft-token" } }
        )
        return OpenIDConnector(strategies)
    }
}

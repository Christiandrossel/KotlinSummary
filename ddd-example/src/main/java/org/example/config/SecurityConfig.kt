package org.example.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() } // Stateless API; adjust if using browser forms
            .authorizeHttpRequests { auth ->
                auth
                    // Allow swagger and static resources
                    .requestMatchers(
                        "/", 
                        "/error",
                        "/v3/api-docs/**",
                        "/swagger-ui.html",
                        "/swagger-ui/**",
                        "/oauth2/**",
                        "/login/**",
                        "/auth/**"
                    ).permitAll()
                    // Protect API endpoints
                    .requestMatchers("/api/**").authenticated()
                    // Everything else permitted (adjust as needed)
                    .anyRequest().permitAll()
            }
            .oauth2Login { oauth2 ->
                // Use our backend-driven login entrypoint and redirect to /me after success
                oauth2.loginPage("/auth/login")
                oauth2.defaultSuccessUrl("/me", true)
            }
            .logout { logout ->
                logout.logoutSuccessUrl("/")
            }

        return http.build()
    }
}

package org.example.controler.model

import jakarta.validation.constraints.Email

data class UpdateUserRequest(
    val name: String?,
    @field:Email(message = "email must be a valid email address")
    val email: String?
)
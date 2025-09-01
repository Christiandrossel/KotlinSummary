package org.example.controler.model

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class CreateUserRequest(
    @field:NotBlank(message = "name must not be blank")
    val name: String,
    @field:NotBlank(message = "email must not be blank")
    @field:Email(message = "email must be a valid email address")
    val email: String
)
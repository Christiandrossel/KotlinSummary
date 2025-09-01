package org.example.controler

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.example.controler.model.CreateUserRequest
import org.example.controler.model.UpdateUserRequest
import org.example.controler.model.UserResponse
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Validated
@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "Operations for managing users")
interface UserApi {

    @Operation(
        summary = "Create a new user",
        description = "Creates a new user with the provided name and email and returns the created resource."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "User created",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = UserResponse::class))]),
            ApiResponse(responseCode = "400", description = "Invalid input", content = [Content()])
        ]
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createUser(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Payload to create a new user",
            required = true,
            content = [Content(schema = Schema(implementation = CreateUserRequest::class))]
        )
        @Valid @RequestBody request: CreateUserRequest
    ): UserResponse

    @Operation(
        summary = "Get user by id",
        description = "Retrieves a single user by its identifier."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "User found",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = UserResponse::class))]),
            ApiResponse(responseCode = "404", description = "User not found", content = [Content()])
        ]
    )
    @GetMapping("/{id}")
    fun getUser(
        @Parameter(description = "User identifier", required = true)
        @PathVariable id: String
    ): UserResponse

    @Operation(
        summary = "Update an existing user",
        description = "Updates the name and/or email of an existing user and returns the updated resource."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "User updated",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = UserResponse::class))]),
            ApiResponse(responseCode = "404", description = "User not found", content = [Content()])
        ]
    )
    @PutMapping("/{id}")
    fun updateUser(
        @Parameter(description = "User identifier", required = true)
        @PathVariable id: String,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Fields to update on the user",
            required = true,
            content = [Content(schema = Schema(implementation = UpdateUserRequest::class))]
        )
        @RequestBody request: UpdateUserRequest
    ): UserResponse

    @Operation(
        summary = "Delete user",
        description = "Deletes a user by its identifier."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "204", description = "User deleted", content = [Content()]),
            ApiResponse(responseCode = "404", description = "User not found", content = [Content()])
        ]
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteUser(
        @Parameter(description = "User identifier", required = true)
        @PathVariable id: String
    )
}






package org.example.controler

import jakarta.validation.Valid
import org.example.controler.model.CreateUserRequest
import org.example.controler.model.UpdateUserRequest
import org.example.controler.model.UserResponse
import org.example.mapper.toApi
import org.example.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val userService: UserService
) : UserApi {

    @ResponseStatus(HttpStatus.CREATED)
    override fun createUser(@Valid request: CreateUserRequest): UserResponse {
        // TODO validate request (e.g., non-empty, valid email) via @Valid annotations in DTOs if needed
        return userService
            .createUser(request.name, request.email)
            .toApi()
    }

    override fun getUser(id: String): UserResponse {
        return userService
            .getUser(id)
            .toApi()
    }

    override fun updateUser(id: String, request: UpdateUserRequest): UserResponse {
        return userService
            .updateUser(id, request.name, request.email)
            .toApi()
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    override fun deleteUser(id: String) {
        // TODO does he have the permission to delete?
        userService.deleteUser(id)
    }

}

@ResponseStatus(HttpStatus.NOT_FOUND)
class NotFoundException(message: String) : RuntimeException(message)
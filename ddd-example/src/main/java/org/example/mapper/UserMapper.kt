package org.example.mapper

import org.example.controler.model.UserResponse
import org.example.repository.model.UserEntity
import org.example.service.model.User

fun User.toApi() = UserResponse(
    id = id ?: "",
    name = name,
    email = email
)

fun User.toEntity() = UserEntity(
    id = null,
    name = name,
    email = email
)

fun UserEntity.toDomain() = User(
    id = id,
    name = name,
    email = email
)

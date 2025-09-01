package org.example.repository

import org.example.repository.model.UserEntity
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository: MongoRepository<UserEntity, String> {

    fun findByUsername(username: String): UserEntity?

    fun findByEmail(email: String): UserEntity?
}
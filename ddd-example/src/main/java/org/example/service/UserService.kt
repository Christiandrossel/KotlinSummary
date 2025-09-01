package org.example.service

import org.example.controler.NotFoundException
import org.example.mapper.toDomain
import org.example.repository.UserRepository
import org.example.repository.model.UserEntity
import org.example.service.model.User
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {

    fun createUser(userName: String, emailAddress: String): User {
        // basic invariants could be placed in domain; keep minimal validation here
        val entity = UserEntity(name = userName, email = emailAddress)
        val savedUser = userRepository.save(entity).toDomain()
        return savedUser
    }

    fun getUser(id: String): User {
        return userRepository.findById(id)
            .orElseThrow { NotFoundException("User $id not found") }
            .toDomain()
    }

    fun updateUser(id: String, name: String?, email: String?): User {
        val current = userRepository.findById(id).orElseThrow { NotFoundException("User $id not found") }
        val updated = current.copy(
            name = name ?: current.name,
            email = email ?: current.email
        )
        return userRepository.save(updated).toDomain()
    }

    fun deleteUser(id: String) {
        if (!userRepository.existsById(id)) throw NotFoundException("User $id not found")
        userRepository.deleteById(id)
    }

}
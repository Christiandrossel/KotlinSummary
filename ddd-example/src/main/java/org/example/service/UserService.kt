package org.example.service

import org.example.controler.NotFoundException
import org.example.mapper.toDomain
import org.example.repository.UserRepository
import org.example.repository.model.UserEntity
import org.example.service.model.User
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val userEventProducer: org.example.messaging.UserEventProducer
) {

    fun createUser(userName: String, emailAddress: String): User {
        // basic invariants could be placed in domain; keep minimal validation here
        val entity = UserEntity(name = userName, email = emailAddress)
        val savedEntity = userRepository.save(entity)
        val savedUser = savedEntity.toDomain()
        // publish event
        val id = savedEntity.id ?: savedUser.id ?: ""
        if (id.isNotBlank()) {
            userEventProducer.userCreated(id, savedUser.name, savedUser.email)
        }
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
        val saved = userRepository.save(updated).toDomain()
        userEventProducer.userUpdated(id = id, name = name, email = email)
        return saved
    }

    fun deleteUser(id: String) {
        val current = userRepository.findById(id).orElseThrow { NotFoundException("User $id not found") }
        userRepository.deleteById(id)
        userEventProducer.userDeleted(id = id, name = current.name, email = current.email)
    }

}
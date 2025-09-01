package org.example.repository.model

import org.springframework.data.annotation.Id

data class UserEntity(
    @Id
    val id: String? = null,

    val name: String,
    val email: String,
    val shoppingList: List<String> = emptyList()
)
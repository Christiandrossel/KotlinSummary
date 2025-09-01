package org.example.service.model

data class User(
    val id: String? = null,
    val name: String,
    val email: String,
    val shoppingList: List<String> = emptyList()
)

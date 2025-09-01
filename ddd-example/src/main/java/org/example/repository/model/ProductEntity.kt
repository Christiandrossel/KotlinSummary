package org.example.repository.model

import org.springframework.data.annotation.Id

data class ProductEntity(
    @Id
    val id: String? = null,
    val name: String,
    val price: Double,
    val description: String,
    val stock: Int,
    val pictures: List<String>,
    val tags: List<String>,
    val vendor: String,
    val active: Boolean
)

package org.example.controler.model

import org.springframework.context.annotation.Description

data class Product(
    val name: String,
    val price: Double,
    val description: String,
    val stock: Int,
    val pictures: List<String>,
    val tags: List<String>,
    val vendor: String,
    val isAvailable: Boolean,
    val category: String
)

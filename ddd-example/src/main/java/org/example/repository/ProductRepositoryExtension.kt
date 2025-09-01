package org.example.repository

import org.example.repository.model.ProductEntity

interface ProductRepositoryExtension {
    fun findProductsByUserShoppingList(userId: String): List<ProductEntity>
}
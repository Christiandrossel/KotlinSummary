package org.example.repository

import org.example.repository.model.ProductEntity
import org.example.repository.model.UserEntity
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query

class ProductRepositoryExtensionImpl(
    private val mongoTemplate: MongoTemplate
) : ProductRepositoryExtension {

    override fun findProductsByUserShoppingList(userId: String): List<ProductEntity> {
        val user: UserEntity? = mongoTemplate.findById(userId, UserEntity::class.java)
        val productIds: List<String> = user?.shoppingList ?: emptyList()
        if (productIds.isEmpty()) return emptyList()
        val query = Query(Criteria.where("_id").`in`(productIds))
        return mongoTemplate.find(query, ProductEntity::class.java)
    }
}
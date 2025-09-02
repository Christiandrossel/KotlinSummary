package org.example.repository

import org.example.controler.model.Product
import org.springframework.data.mongodb.repository.MongoRepository

interface ProductRepository: MongoRepository<Product, String>, ProductRepositoryExtension {

    @org.springframework.data.mongodb.repository.Query("{ '\$or': [ { 'price': { '\$gt': ?0 } }, { 'tags': { '\$in': ?1 } } ], 'available': true }")
    fun findFilteredProductsByPriceAndTax(minPrice: Double, tags: List<String>): List<Product>
}
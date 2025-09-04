package org.example.repository

import org.example.controler.model.Product
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository: MongoRepository<Product, String>, ProductRepositoryExtension {
}
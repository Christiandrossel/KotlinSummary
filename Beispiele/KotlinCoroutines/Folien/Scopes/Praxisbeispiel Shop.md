Natürlich! 🚀  
Hier ist ein einfaches **Spring Boot Backend** für einen Shop. Es enthält:
- **Produkte (Product) verwalten**
- **Bestellungen (Order) erstellen**
- Verwendung von **Kotlin Coroutines** für asynchrone Verarbeitung
- Nutzung von **Coroutine-Scopes** für saubere Fehlerbehandlung und Performance

Wir verwenden:
- **Spring Boot** mit **WebFlux** (für Reactive-Programming)
- **R2DBC** für asynchrone Datenbankzugriffe (PostgreSQL)
- **Kotlin Coroutines** für parallele Verarbeitung

---

# 🗂️ **1️⃣ Projektstruktur**

```
shop-backend/
├── src/main/kotlin/com/example/shop
│   ├── ShopApplication.kt
│   ├── controller
│   │   ├── ProductController.kt
│   │   └── OrderController.kt
│   ├── model
│   │   ├── Product.kt
│   │   └── Order.kt
│   ├── repository
│   │   ├── ProductRepository.kt
│   │   └── OrderRepository.kt
│   └── service
│       ├── ProductService.kt
│       └── OrderService.kt
└── resources
    └── application.yml
```

---

# 🚀 **2️⃣ `build.gradle.kts`**

```kotlin
plugins {
    kotlin("jvm") version "1.8.21"
    kotlin("plugin.spring") version "1.8.21"
    id("org.springframework.boot") version "3.1.0"
    id("io.spring.dependency-management") version "1.1.0"
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")

    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("io.r2dbc:r2dbc-postgresql")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
}
```

---

# 🌍 **3️⃣ `application.yml`**

```yaml
server:
  port: 8080

spring:
  r2dbc:
    url: r2dbc:postgresql://localhost:5432/shopdb
    username: postgres
    password: password
  datasource:
    driver-class-name: org.postgresql.Driver

logging:
  level:
    org.springframework: INFO
```

---

# 📦 **4️⃣ `ShopApplication.kt`**

```kotlin
package com.example.shop

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ShopApplication

fun main(args: Array<String>) {
    runApplication<ShopApplication>(*args)
}
```

---

# 📊 **5️⃣ Model Layer**

## ✅ `Product.kt`

```kotlin
package com.example.shop.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("products")
data class Product(
    @Id val id: Long? = null,
    val name: String,
    val price: Double,
    val stock: Int
)
```

## ✅ `Order.kt`

```kotlin
package com.example.shop.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("orders")
data class Order(
    @Id val id: Long? = null,
    val productId: Long,
    val quantity: Int
)
```

---

# 📚 **6️⃣ Repository Layer**

## ✅ `ProductRepository.kt`

```kotlin
package com.example.shop.repository

import com.example.shop.model.Product
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : CoroutineCrudRepository<Product, Long>
```

## ✅ `OrderRepository.kt`

```kotlin
package com.example.shop.repository

import com.example.shop.model.Order
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : CoroutineCrudRepository<Order, Long>
```

---

# ⚙️ **7️⃣ Service Layer (mit Coroutines)**

## ✅ `ProductService.kt`

```kotlin
package com.example.shop.service

import com.example.shop.model.Product
import com.example.shop.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service

@Service
class ProductService(private val productRepository: ProductRepository) {

    suspend fun getAllProducts(): List<Product> = withContext(Dispatchers.IO) {
        productRepository.findAll().toList()
    }

    suspend fun addProduct(product: Product): Product = withContext(Dispatchers.IO) {
        productRepository.save(product)
    }

    suspend fun getProductById(id: Long): Product? = withContext(Dispatchers.IO) {
        productRepository.findById(id)
    }
}
```

## ✅ `OrderService.kt`

```kotlin
package com.example.shop.service

import com.example.shop.model.Order
import com.example.shop.repository.OrderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val productService: ProductService
) {

    suspend fun placeOrder(order: Order): String = withContext(Dispatchers.IO) {
        val product = productService.getProductById(order.productId)
            ?: return@withContext "Produkt nicht gefunden"

        if (product.stock < order.quantity) {
            return@withContext "Nicht genug Lagerbestand"
        }

        orderRepository.save(order)
        productService.addProduct(product.copy(stock = product.stock - order.quantity))

        "Bestellung erfolgreich"
    }
}
```

---

# 🌐 **8️⃣ Controller Layer**

## ✅ `ProductController.kt`

```kotlin
package com.example.shop.controller

import com.example.shop.model.Product
import com.example.shop.service.ProductService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/products")
class ProductController(private val productService: ProductService) {

    @GetMapping
    suspend fun getAllProducts() = withContext(Dispatchers.Default) {
        productService.getAllProducts()
    }

    @PostMapping
    suspend fun addProduct(@RequestBody product: Product) = withContext(Dispatchers.Default) {
        productService.addProduct(product)
    }
}
```

## ✅ `OrderController.kt`

```kotlin
package com.example.shop.controller

import com.example.shop.model.Order
import com.example.shop.service.OrderService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/orders")
class OrderController(private val orderService: OrderService) {

    @PostMapping
    suspend fun placeOrder(@RequestBody order: Order) = withContext(Dispatchers.Default) {
        orderService.placeOrder(order)
    }
}
```

---

# 🗃️ **9️⃣ SQL für PostgreSQL**

```sql
CREATE TABLE products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DOUBLE PRECISION NOT NULL,
    stock INT NOT NULL
);

CREATE TABLE orders (
    id SERIAL PRIMARY KEY,
    product_id BIGINT REFERENCES products(id),
    quantity INT NOT NULL
);
```

---

# 🚀 **🔍 API-Test mit cURL/Postman**

1️⃣ **Produkt hinzufügen**
```bash
curl -X POST http://localhost:8080/products -H "Content-Type: application/json" -d '{"name": "Laptop", "price": 1200.50, "stock": 10}'
```

2️⃣ **Alle Produkte abrufen**
```bash
curl http://localhost:8080/products
```

3️⃣ **Bestellung aufgeben**
```bash
curl -X POST http://localhost:8080/orders -H "Content-Type: application/json" -d '{"productId": 1, "quantity": 2}'
```

---

# ⚡ **Was passiert hier mit den Coroutines?**

- **Datenbankzugriffe** laufen asynchron mit `Dispatchers.IO`.
- **Business-Logik** wird im Standard-Dispatcher verarbeitet (`Dispatchers.Default`).
- Fehlerbehandlung ist einfach durch `try-catch` in den Services integrierbar.
- **Hohe Skalierbarkeit**, da Spring WebFlux und R2DBC non-blocking sind.

---

# ❓ **Möchtest du noch erweiterte Funktionen wie Authentifizierung, mehr Fehlerhandling oder asynchrone Event-Verarbeitung?**
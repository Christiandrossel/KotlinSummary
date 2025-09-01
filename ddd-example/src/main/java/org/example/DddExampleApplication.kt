package org.example

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@SpringBootApplication
@EnableMongoRepositories
class DddExampleApplication

fun main(args: Array<String>) {
    runApplication<DddExampleApplication>(*args)
}

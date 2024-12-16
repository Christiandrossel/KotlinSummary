package net.avgl.ekz.onleihe

import java.time.LocalDate

data class Person(
    val name: String,
    val lastname: String,
    val birthdate: LocalDate,
    // Money
    val salary: Double,
)


class Excersice {

    fun main() {
        val person = Person("John", "Doe", LocalDate.of(1990, 1, 1), 1000.0)
        println(person)
    }
}
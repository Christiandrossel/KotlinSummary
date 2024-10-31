package CreationalPatterns.Abstract_Factory

interface Car {
    fun assemble()
}

class Sedan : Car {
    override fun assemble() {
        println("Assembling Sedan car.")
    }
}

class Hatchback : Car {
    override fun assemble() {
        println("Assembling Hatchback car.")
    }
}

interface CarSpecification {
    fun display()
}

class NorthAmericaSpecification : CarSpecification {
    override fun display() {
        println("North America Car Specification: Safety features compliant with local regulations.")
    }
}

class EuropeSpecification : CarSpecification {
    override fun display() {
        println("Europe Car Specification: Fuel efficiency and emissions compliant with EU standards.")
    }
}

interface CarFactory {
    fun createCar(): Car
    fun createSpecification(): CarSpecification
}

class NorthAmericaCarFactory : CarFactory {
    override fun createCar(): Car = Sedan()
    override fun createSpecification(): CarSpecification = NorthAmericaSpecification()
}

class EuropeCarFactory : CarFactory {
    override fun createCar(): Car = Hatchback()
    override fun createSpecification(): CarSpecification = EuropeSpecification()
}

fun main() {
    val northAmericaFactory = NorthAmericaCarFactory()
    val northAmericaCar = northAmericaFactory.createCar()
    val northAmericaSpec = northAmericaFactory.createSpecification()

    northAmericaCar.assemble()
    northAmericaSpec.display()

    val europeFactory = EuropeCarFactory()
    val europeCar = europeFactory.createCar()
    val europeSpec = europeFactory.createSpecification()

    europeCar.assemble()
    europeSpec.display()
}
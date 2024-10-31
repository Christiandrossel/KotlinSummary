package CreationalPatterns.Builder

/**
 * Builder Pattern is a creational design pattern that lets you construct complex objects step by step.
 *
 */

// Product
class Computer {
    private var cpu: String = ""
    private var ram: String = ""
    private var storage: String = ""

    fun setCPU(cpu: String) {
        this.cpu = cpu
    }

    fun setRAM(ram: String) {
        this.ram = ram
    }

    fun setStorage(storage: String) {
        this.storage = storage
    }

    fun displayInfo() {
        println("Computer Configuration:\nCPU: $cpu\nRAM: $ram\nStorage: $storage\n")
    }
}

// Builder interface
interface Builder {
    fun cpu()
    fun ram()
    fun storage()
    fun getResult(): Computer
}

// ConcreteBuilder
class GamingComputerBuilder : Builder {
    private val computer = Computer()

    override fun cpu() {
        computer.setCPU("Gaming CPU")
    }

    override fun ram() {
        computer.setRAM("16GB DDR4")
    }

    override fun storage() {
        computer.setStorage("1TB SSD")
    }

    override fun getResult(): Computer {
        return computer
    }
}

// Director
class ComputerDirector {
    fun construct(builder: Builder) {
        builder.cpu()
        builder.ram()
        builder.storage()
    }
}

// Client
fun main() {
    val gamingBuilder = GamingComputerBuilder()
    val director = ComputerDirector()

    director.construct(gamingBuilder)
    val gamingComputer = gamingBuilder.getResult()

    gamingComputer.displayInfo()
}
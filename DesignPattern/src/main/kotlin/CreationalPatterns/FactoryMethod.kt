package CreationalPatterns

/**
 * Factory Method is a creational design pattern that provides an interface for creating objects in a superclass,
 * but allows subclasses to alter the type of objects that will be created.
 */
fun main() {
    println("App: Launched with the ConcreteCreatorA.")
    val creatorA = FactoryMethod.ConcreteCreatorA()
    FactoryMethod().clientCode(creatorA)

    val creatorB = FactoryMethod.ConcreteCreatorB()
    FactoryMethod().clientCode(creatorB)


    println("\nApp: Launched with the WindowsDialog.")
    val app = Application()
    app.initialize()
    app.dialog.render()

}

class FactoryMethod {

    interface Product {
        fun operation(): String
    }

    class ConcreteProductA : Product {
        override fun operation(): String = "{Result of ConcreteProductA}"
    }

    class ConcreteProductB : Product {
        override fun operation(): String = "{Result of ConcreteProductB}"
    }

    abstract class Creator {
        abstract fun factoryMethod(): Product

        fun someOperation(): String {
            val product = factoryMethod()
            return "Creator: The same creator's code has just worked with ${product.operation()}"
        }
    }

    class ConcreteCreatorA : Creator() {
        override fun factoryMethod(): Product = ConcreteProductA()
    }

    class ConcreteCreatorB : Creator() {
        override fun factoryMethod(): Product = ConcreteProductB()
    }

    fun clientCode(creator: Creator) {
        println("Client: I'm not aware of the creator's class, but it still works.\n${creator.someOperation()}")
    }
}


interface Dialog {
    fun createButton(): Button

    fun render() {
        val okButton = createButton()
        okButton.onClick()
        okButton.render()
    }
}

class WindowsDialog : Dialog {
    override fun createButton(): Button {
        return WindowsButton()
    }
}

class WebDialog : Dialog {
    override fun createButton(): Button {
        return HTMLButton()
    }
}

interface Button {

    fun render()
    fun onClick()
}

class WindowsButton : Button {
    override fun render() {
        println("Render a button in Windows style.")
    }

    override fun onClick() {
        println("Bind a native OS click event.")
    }
}

class HTMLButton : Button {
    override fun render() {
        println("Return an HTML representation of a button.")
    }

    override fun onClick() {
        println("Bind a web browser click event.")
    }
}

class Application {
    lateinit var dialog: Dialog

    fun initialize() {
        val config = readApplicationConfigFile()

        dialog = when {
            config.OS == "Windows" -> WindowsDialog()
            config.OS == "Web" -> WebDialog()
            else -> throw Exception("Error! Unknown operating system.")
        }
    }

    private fun readApplicationConfigFile(): Config {
        return Config("Windows")
    }

    data class Config(val OS: String)
}
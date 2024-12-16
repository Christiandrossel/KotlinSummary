/**
 * Unterschied in Klassen zwischen Key Wort val/ var
 * und keinem Key wort
 */

class Test(
    withoutValVar: String,
    val withVal: String,
    var withVar: String
) {
    var parameter = withoutValVar
    var variable = withVar
    val value = withVal

    fun printAllParamters() {
        println(parameter)
        println(withVal)
        println(withVar)
//        println(withoutValVar) // kein zugriff, da keine Getter u Setter verfügbar
    }
}

class Main {
    init {
        val test = Test("withoutAll", "with val", "with var")
        test.printAllParamters()
    }
}

Main()
/**
 * interfaces und abstrakte Klassen
 * und vererbung
 */
interface InterfaceLicenceOwner {
    var name: String
    val street: String
}

abstract class AbstractLicenceOwner(
    open var name: String,
    open var street: String
) {
//    fun getName(): String {
//        return name
//    }
}

class Onleihe(
    override var name: String,
    override var street: String
): AbstractLicenceOwner(name, street) {

}

/**
 * Wenn variablen ohne var/ val im Konstruktor definiert werden, dann sind diese 
 * Konstruktor variablen und darüber hinaus nicht verwendbar
 * Sie existieren nur inerhalb der Konstruktor Methode. 
 * Es kann nicht mehr danach darauf zugegriffen werden.
 */
abstract class Plant(
    name: String,
    weight: Long
)
class Flower(
    var name: String,
    weight: Long
): Plant(name, weight) {
    
}


val onleihe = Onleihe("onl", "street")
val abstractOnleihe = onleihe as AbstractLicenceOwner
println(onleihe.name)
println(abstractOnleihe.name)
//println(onleihe.getName())
// Change name
onleihe.name = "onleihe"
println(onleihe.name)
println(abstractOnleihe.name)

var flower = Flower("fl", 132)
var abstractFlower = flower as Plant
println(flower.name)
/**
 * Folgendes Beispiel zeigt, dass die nicht mehr auf die variable name 
 * zugegriffen werden kann
 */
println(abstractFlower.name) 

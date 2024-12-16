/**
 * In diesem Beispiel wird gezeigt, wie die Vererbung mit einem Feld funktioniert, das in der Basisklasse optional ist,
 * in der abgeleiteten Klasse jedoch obligatorisch ist.
 */


interface Restrictions {
    val lend: Int
}

data class OnleiheRestrictions(
    override val lend: Int
): Restrictions

data class LibraryRestrictions(
    override val lend: Int
): Restrictions


// Basisklasse, die alle gemeinsamen Eigenschaften enthält
abstract class LicenceOwner
    (
    override val id: String,
    override val externalId: String,
    override val name: String,
    override var state: String,
    override var restrictions: Restrictions? = null
): ILicenceOwner {
    abstract fun getAllLicenceOwnerIds(): List<String>
}

interface ILicenceOwner
{
   val id: String
   val externalId: String
   val name: String
   var state: String
   val restrictions: Restrictions?  //Damit dieses Feld in der eine Klasse nicht null sein muss, muss es hier auch nullable sein und val statt var sein!!
}


// UserLibrary-Klasse, die von der Basisklasse erbt
data class Library(
    override val id: String,
    override val externalId: String,
    override val name: String,
    override var state: String,
    // Die restrictions sind optional und können null sein
    override var restrictions: Restrictions? = null
) : LicenceOwner(id, externalId, name, state, restrictions) {
    override fun getAllLicenceOwnerIds(): List<String> {
        TODO("Not yet implemented")
    }
}

// CustomerLibrary-Klasse, die ebenfalls von der Basisklasse erbt
// Hier ist die restrictions-Eigenschaft obligatorisch
data class Onleihe(
    override var id: String,
    override var externalId: String,
    override var name: String,
    override var state: String,
    override var restrictions: Restrictions // Diese Eigenschaft muss initialisiert werden
) : ILicenceOwner by LicenceOwner(id, externalId, name, state, restrictions)

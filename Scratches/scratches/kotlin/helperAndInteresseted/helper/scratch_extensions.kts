/**
 * Dieser Extension fügt Jeden Objekt (Any?) die Methode nullOrEmpty() hinzu.
 * Die Methode überprüft, ob das Objekt null oder leer ist.
 */
fun Any?.nullOrEmpty(): Boolean {
    return when {
        this == null -> true
        this is String -> this.isEmpty()
        this is Collection<*> -> this.isEmpty()
        else -> false
    }
}

/**
 * Beispiel:
 */
fun exampleNullOrEmpty() {
    val list = listOf<String>()
    println(list.nullOrEmpty()) // true
    println("".nullOrEmpty()) // true
    println(null.nullOrEmpty()) // true
    println("Hallo".nullOrEmpty()) // false
    println(listOf("Hallo").nullOrEmpty()) // false
}

exampleNullOrEmpty()
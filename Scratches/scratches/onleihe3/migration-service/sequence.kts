    val sequence = sequenceOf(1, 2, 3).map {
        println("Mapping $it")
        it * 2
    }.filter {
        println("Filtering $it")
        it % 2 == 0
    }

// Der Aufruf zur Verarbeitung der Sequenz
println(sequence.toList())
var isConainer = "true"

isConainer.toBoolean()
isConainer.toBooleanStrict()

isConainer = "false"

isConainer.toBoolean()
isConainer.toBooleanStrict()

isConainer = "True"

isConainer.toBoolean()
try {
    isConainer.toBooleanStrict()
} catch (e: Exception) {
    println(e)
}
isConainer = "False"

isConainer.toBoolean()
try {
    isConainer.toBooleanStrict()
} catch (e: Exception) {
    println(e)
}

isConainer = "TRUE"

isConainer.toBoolean()
try {
    isConainer.toBooleanStrict()
} catch (e: Exception) {
    println(e)
}

isConainer = "ABC"

isConainer.toBoolean()
try {
    isConainer.toBooleanStrict()
} catch (e: Exception) {
    println(e)
}
import kotlin.random.Random

/** Regex expression **/
// soll einen empty user erkennen der am ende eine nummer hat
val emptyUser = Regex("^empty-user-\\d+$")
println(emptyUser.matches("empty-user-1"))
println(emptyUser.matches("empty-user-123"))
println(emptyUser.matches("empty-user-000"))
println(emptyUser.matches("empty-user-1asd"))
println(emptyUser.matches("empty-user-jfjk"))
println(emptyUser.matches("empty-user-"))
println(emptyUser.matches("empty-user-123-123"))

"Library-${Random.nextInt()}"
"Library-${Random.nextInt()}"
"Library-${Random.nextInt()}"
"Library-${Random.nextInt()}"

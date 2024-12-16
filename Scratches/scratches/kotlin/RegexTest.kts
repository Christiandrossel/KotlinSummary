import kotlin.text.Regex

val regex = Regex("(password|clientSecret|privateKey|openIdCode" +
    "|clientId|accessToken|idToken|refreshToken|" +
    "accountName|libraryNumber|notificationEmail)=(\\w+)")

// message with sensitive information
val message = "This is a test log with password=secret123, clientSecret=clientSecret123, privateKey=privateKey"

// michas version
fun filterMessageV01(message: String): String {
    val matchResult = regex.find(message)
    if (matchResult?.groups?.size == 3) {
        return message.replace(matchResult.groups[2]!!.value, "****")
    }
    return message
}

fun filterMessageV02(message: String): String {
    val matchResult = regex.find(message)
    if (matchResult != null) {
        val sensitiveInformation = matchResult.groupValues[1]
        val maskedSensitiveInformation = matchResult.groupValues[2]
        return message.replace(sensitiveInformation, maskedSensitiveInformation)
    }
    return message
}

fun filterMessageV03(message: String): String {
    val matchResult = regex.find(message)
    if (matchResult?.groups?.size == 3) {
        val sensitiveInformation = matchResult.groups[1]!!.value
        val maskedSensitiveInformation = matchResult.groups[2]!!.value
        return message.replace(sensitiveInformation, maskedSensitiveInformation)
    }
    return message
}

// test
val maskedMessageV01 = filterMessageV01(message)
val maskedMessageV02 = filterMessageV02(message)
val maskedMessageV03 = filterMessageV03(message)
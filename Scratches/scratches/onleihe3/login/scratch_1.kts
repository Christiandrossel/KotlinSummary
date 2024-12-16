import net.avgl.ekz.onleihe.secretmanager.model.SafeSecret
import net.avgl.ekz.onleihe.userapplication.user_authentication_service.model.LoginInformation
import net.avgl.ekz.onleihe.userapplication.user_authentication_service.model.login.DefaultOpenIdLoginMethod
import org.slf4j.Logger
import org.slf4j.LoggerFactory

// Log pattern

object LoggerUtil {
//    private val logger: Logger = LoggerFactory.getLogger(LoggerUtil::class.java)
    private val sensitiveDataPattern = Regex("(password=\\w+|clientSecret=\\w+|privateKey=\\w+)") //TODO diese auch anonymisieren |idToken=\w+|refreshToken=\w+|accessToken=\w+|code=\w+|redirectUri=\w+

    fun logInfo(message: String, vararg args: Any?) {
        val sanitizedMessage = sensitiveDataPattern.replace(message) { matchResult ->
            matchResult.value.split("=").first() + "=****"
        }
//        logger.info(sanitizedMessage, *args)
        println(sanitizedMessage)
        args.forEach {
            println(it)
        }
    }
}


fun main() {
    val log = LoggerUtil

    val loginInformation = LoginInformation(
        "username",
        "password",
        "externalLibraryId"
    )

    val defaultOpenIdLoginMethod = DefaultOpenIdLoginMethod(
        "authorizationEndpoint",
        "tokenEndpoint",
        "cliendId",
        SafeSecret("clientSecret/Path/Here"),
        false,
        null,
        null,

    )

    log.logInfo("Authenticates the user with the UPA service.", loginInformation)
    log.logInfo("defaultOpenIdLoginMethod", defaultOpenIdLoginMethod)
}

main()
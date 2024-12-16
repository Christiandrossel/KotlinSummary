package net.avgl.ekz.onleihe.userapplication.user_authentication_service.service

import net.avgl.ekz.onleihe.userapplication.test_data_service.util.LibraryCreator.createLibraries
import net.avgl.ekz.onleihe.userapplication.user_authentication_service.connector.SwitchConnector
import net.avgl.ekz.onleihe.userapplication.user_authentication_service.model.login.SwitchOpenIdLoginMethod
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@Disabled
class SwitchAuthenticationTest(
    @Autowired private val switchConnector: SwitchConnector
) {



    @Test
    @Disabled
    fun `should authenticate to keycloack`() {
        // given
        val uri = "http://localhost:8081/userprovider/ws/openid/authorize?state=IGM5sfgnOnT5DP2dweMdU4SxDpFVcc&session_state=2aed1208-bf98-46d7-8ba9-45c2ea6424bd&code=275c4e9c-8ca0-4740-a4ba-29a4e5e24c53.2aed1208-bf98-46d7-8ba9-45c2ea6424bd.ea28e3f9-e5d0-4a35-85e3-f556c2ea469e"
//        val code = "5deb926b-c35c-4c83-b365-16cbd2160a74.664cdcf0-0fd9-4783-ae6d-5a9e8e4c6847.ea28e3f9-e5d0-4a35-85e3-f556c2ea469e"
        val code = "5217bfb4-3a09-4d3b-bb23-984606970707.cb11f2c4-c715-415a-b54d-17c21a0dc89a.ea28e3f9-e5d0-4a35-85e3-f556c2ea469e"
        val redirectUri = "https://ci-divibib.dotsource.de"
        val library = createLibraries().find { it.id == "test-switch-dotsource" }!!
        val keycloakUri = "https://keycloak-staging.onleihe.de/realms/test/protocol/openid-connect/auth?client_id=dotsourcePrivateKeyJWT&scope=openid&prompt=consent&response_type=code&redirect_uri=https://ci-divibib.dotsource.de"


        // when
        val token = switchConnector.auth(code, redirectUri, library.loginMethod as SwitchOpenIdLoginMethod, library)

        // then
        println("tokenResponse: $token")
        assert(token.idToken.isNotBlank())
        assert(token.accessToken.isNotBlank())
    }

    @Test
    @Disabled
    fun `should authenticate to "Aargauer Kantonsbibliothek"`() {
        // given
        //login argauer lib
        val argauerLoginUrl = "https://ebookplus.onleihe.com/verbund_baden/frontend/login,0-0-0-800-0-0-0-0-0-0-0.html?libraryId=2234"
        val switchTestURL = "https://login.eduid.ch/idp/profile/oidc/authorize?response_type=code&client_id=divibib001&redirect_uri=http://localhost:8081/userprovider/ws/openid/authorize&scope=openid+https%3A%2F%2Flogin.eduid.ch%2Fauthz%2FUser.Read&state=X9PIWhfaFIazbgkT3Ooak0eyms84o9"
        val username = "testuser1-switch@onleihe.de"
        val password = "9qZdSC29TU1"
        //code to get from login
        val code = ""
        val redirectUri = "http://localhost:8081/userprovider/ws/openid/authorize"
        val library = createLibraries().find { it.id == "test-library-switch-01" }!!

        // when
        val token = switchConnector.auth(code, redirectUri, library.loginMethod as SwitchOpenIdLoginMethod, library)

        // {"error":"invalid_client","error_description":"Client authentication failed"}

        // then
        println("tokenResponse: $token")
        assert(token.idToken.isNotBlank())
        assert(token.accessToken.isNotBlank())
    }

}
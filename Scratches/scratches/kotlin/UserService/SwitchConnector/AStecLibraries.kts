private fun createLibrary(
    id: String = LIBRARY,
    externalId: String? = null,
    name: String? = null,
    onleiheId: String = ONLEIHE,
    state: LibraryState = LibraryState.ACTIVE,
    address: Address = Address(
        city = "Dresden",
        houseNumber = null,
        postalCode = null,
        street = null,
    ),
    loginMethod: LoginMethod? = UpaLoginMethod(),
) = Library(
    id = id,
    externalId = externalId ?: (EXTERNAL_ID + id),
    onleiheId = onleiheId,
    name = name ?: id,
    state = state,
    address = address,
    loginMethod = loginMethod
)

//AsTec
createLibrary(
    id = "Test-Library-asTec-01", loginMethod = DefaultOpenIdLoginMethod(
        authorizationEndpoint = "https://login.eduid.ch/idp/profile/oidc/authorize",
        tokenEndpoint = "https://login.eduid.ch/idp/profile/oidc/token",
        userInfoEndpoint = "https://login.eduid.ch/idp/profile/oidc/userinfo",
        clientId = "divibib001",
        clientSecret = "cf16e5e3-d296-4de2-994f-a8d9594e7415",
        supportForRefreshToken = false,
    )
)
createLibrary(
    id = "Test-Library-asTec-02",
    loginMethod = DefaultOpenIdLoginMethod(
        authorizationEndpoint = "https://ssl.muenchen.de/oidcp/authorize",
        tokenEndpoint = "https://ssl.muenchen.de/oidcp/token",
        userInfoEndpoint = "https://ssl.muenchen.de/oidcp/userinfo" ,
        clientId = "onleihe001",
        clientSecret = "7Jbd-10zBQiHCSNbHhkJJZx5TNzNABNXoM7YCerFagE",
        supportForRefreshToken = true,
    )
)
createLibrary(
    id = "Test-Library-asTec-03",
    loginMethod = DefaultOpenIdLoginMethod(
        authorizationEndpoint = "https://login.eduid.ch/idp/profile/oidc/authorize",
        tokenEndpoint = "https://login.eduid.ch/.well-known/openid-configuration ",
        userInfoEndpoint = "https://login.eduid.ch/idp/profile/oidc/userinfo",
        clientId = "divibib001",
        clientSecret = "cf16e5e3-d296-4de2-994f-a8d9594e7415",
        supportForRefreshToken = true,
    )
)
createLibrary(
    id = "Astec-muenchen",
    loginMethod = DefaultOpenIdLoginMethod(
        authorizationEndpoint = "https://ssl.muenchen.de/oidcp/authorize",
        tokenEndpoint = "https://login.eduid.ch/.well-known/openid-configuration ",
        userInfoEndpoint = "https://ssl.muenchen.de/oidcp/userinfo" ,
        clientId = "divibib001",
        clientSecret = "cf16e5e3-d296-4de2-994f-a8d9594e7415",
        supportForRefreshToken = true,
    )
)
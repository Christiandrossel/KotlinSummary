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

fun createSwitchLibrary() =
//Switch
createLibrary(
    id = "test-library-switch-01",
    loginMethod = SwitchOpenIdLoginMethod(
        authorizationEndpoint = "https://login.eduid.ch/idp/profile/oidc/authorize",
        tokenEndpoint = "https://login.eduid.ch/idp/profile/oidc/token",
        userInfoEndpoint = "https://login.eduid.ch/idp/profile/oidc/userinfo", //https://login.eduid.ch/authz/User.Read
        clientId = "divibib001",
        //scope = "openid",
        scope = "openid https://login.eduid.ch/authz/User.Read",
//                    scope = "openid+https%3A%2F%2Flogin.eduid.ch%2Fauthz%2FUser.Read", //--> 401 Unauthorized from POST https://login.eduid.ch/idp/profile/oidc/token
//                    scope = "https://login.eduid.ch/authz/User.Read",
        privateKey = "-----BEGIN RSA PRIVATE KEY-----\n" +
                "MIIJKgIBAAKCAgEAsilWtc7MNqKmRgxKk/+lDABQRjIn5buhknV1BpZjrI5zg5nl\n" +
                "3NFg4ARBtunYKhqIeiKpb/v5ZrkmSNf6n3kZjytJLhxkhECZ9MAZygxYsYnCRiB4\n" +
                "WsX1AZXcRTwfOc8reWu8oHmXsuOX8MyUzZLzznz4UT3/h8dzRnghFVOr1EgMITo/\n" +
                "r+n6zFhHoWs6hF1F5APgewjLnww+e5G5xR6Vd6SnZajjfLnI4t0Nd7gpXzy4Ql3y\n" +
                "3NcF5j4d6DwZSJL9NdpG5/bllkbQ3b+LVqX2t0wU74NfxVLKtGlQ8knSEWKrkHMt\n" +
                "/LgbvwjETUuEDIXWefzqOoZB1MoqvTDw2ouk+30p4f4iOK8EYOs0p0cwp8jTWLwc\n" +
                "X9oRgbl2sT76FQ7d0EramGhj24BLCvy2YVeMLIXLVeak1wcD02KDkyl6CD5F02N9\n" +
                "zmUhwZdf0BVFn54vVmxhUZZyWYd5iNpHn3GXWgGhVpi3CldjQHoZhGjjD6a1g6BF\n" +
                "2B7u1WLmkx/yWb7uz3YkDoTdJrCRyFeTOrN8wjg0POrD91338KAMN+LcT9MYKN28\n" +
                "zpXV9F6/huY4zz06pEQ8yLOgXlDTzwri8HPhByiEH48qYchiFLsijAJDA8+aJNxP\n" +
                "TA1ibGEvovbDZc6ufdDh5c1hBY/I6HPFuyWAmEz0yOYdt208TUz5cJZg/UECAwEA\n" +
                "AQKCAgEAsg7krWHX8UbPfTStmIqojn0FvZJFE7JZwcGISyQkzAG26zD1baMI+frn\n" +
                "HoNJ2pKYEdDwnI4tbX/gBm9s+NMUO2VchywzPYY/GEChxbBfOFjhBCtRfg9YVdRL\n" +
                "yBpVbvZhkcGeTJgZh901LqGYmnijGJ0HpFKdPzuUNzq6fdEY61lyXgcUPaFQWchl\n" +
                "FqvPWRjLUB6bzPs2XmfjTqaPDrYJVgEGmHCZoz8eybV5z0wMdTV/Rj0VC6gPKlSs\n" +
                "mTzvW/wj+x2DPjQgsvdlqinUfa3zDLCKP/KB1flvw616DBzBSUTfCMhEjs4GVP/T\n" +
                "CRlPCSD+AP9HrWQfCSdgXaM7JIDqSg9o+p1yfcBb3/7pvZfCWvCOdUFvOfNRaZ81\n" +
                "pWvmO2pp7HKx3XLqxdyn+/A/59qrz40QJJJbDv5DxC9KQ3XW43BzRVLL/q67J4Xr\n" +
                "lOjf3UkXescAjZMvgVXTKR+tMy3h8ojbOKqh/Ok1fW3YJg+DiwZyt8oi8RdmbuPj\n" +
                "0EHIXNuj23cWjbX7Wd34+CR61g28eBh3rlw+ACDt03+dvPpSA9XW5h8UFYB9J2/4\n" +
                "4CmrEQoH2iU/7Red/u4r15klCkPA+0v50sC9amm0jL/p4AsAc6Wbstd6zXjKsLa+\n" +
                "sTTVKajunKPCi+Wwq7z9zd9qKMSi6kbcFQB6PqfTtghX/B1Rau0CggEBANyyrrFd\n" +
                "m/UJKak20YlpROdcdyT4XeBBNXE+dMH/nmsE2VKbX5A1uU9nHf6EHnKcfmqFbpr1\n" +
                "UCMa+OtUzPMrISOZ0tjFnhdxMHyY62BYN6nnPo8zobtIaQ4q+oWxcEvK4hYGh5ok\n" +
                "Vj4DyQERHDTvN8SKrnBvEd8aUJ2dnENqW0GuAeheW8BTGrFaxEl4xaEvAqiXDYQG\n" +
                "uF+uZzw0h02MCm6TvR4HPLbyHbrK8dOUo5vWEbDEWeSYkDuAgAYzt5Lz628ygPZN\n" +
                "Zt6x4FiAyp6KVmEeZp42GEfJ/aBOXhWBBygHeLk4XFZmrCwtCR4z0mA2/zBXGBK1\n" +
                "/oPuNoHNwB0Zaj8CggEBAM6o1nLsBTk7+0OIUkBGTIZST34h6Umzey1gFp31k55j\n" +
                "1i3jqXes9r4ugC08NzqsaClKPEl4k5lsVASxx2VgW1kiUd5n9zq2/MdDt+r/cmbV\n" +
                "4nz7dATG36KLBR7rGpC9zGICKmjua8+6a/sJnnkiPQzMQEd2dRnCfd/3HeKTP3mk\n" +
                "kN4OSHWjlNarUb/iLGc6f/J7srvMLcfet4YRZlSF6f1zymJo4TTImMbjUXFSoCMi\n" +
                "btc0JpkM6kY7mtKnWlT3ojHL6Ysh07XmwdCcBprZJnnrDOFJJL/Ub+udChE77zp0\n" +
                "HCmf8mvpLnq+rIABAqU7SCEi/Dq+aU66YZXs+fOYuH8CggEBAI0iHyEBkdhTMbcq\n" +
                "fTj4fr3NncugoLfmO3zdyeC9zNr3UNrIzYX1NToX4VB/wNDeTuFjzuV9du/cMc6c\n" +
                "fiwLKrgPiIxjY8Pt+GnViMAl6gLXBGSjSgvwNG6BAZA5dmyho48eQd1K23PxC7uI\n" +
                "65bWW71uSjtQbAdWVrNUtTdPbmhEFKg6n0YQXwOH3fp2Jzv6SiLx5gzGF2Xoq9AO\n" +
                "b3Ah3BH2nHv1eNgtYOGxIpCBf1cKwGlgR3QuNB45AKX2sLBjF+4WR+46dUx4R9WR\n" +
                "FwHnyXF19X27bi+KdozrULNDvgXVMViROEtwgS4G5svPq/sncG3DZHuiss4/qc+8\n" +
                "edyiAhUCggEAezTtpVZRRu4bvwdnAirnNK2d9vUAwI/gwAypb0MZ3zRmXLV+M3tL\n" +
                "H9dZU56yzqg68WLwzly0oqOS87mJx0bLLzjm1lHlc3vk+GfHHrNs4SJjBWBkqvRm\n" +
                "H+ICC4zfKav+TfA8mJKMdCwQdHonoNBo1VhweiqFZfYuVXheuNZQ4ZTbSYf6ayg7\n" +
                "dEBvXJbbIjAeV9KnnI7+PErr32JAP+QWvKRr5H/ixDVT4D9mLsmz7z54FpVg7Z5l\n" +
                "jbm7dBuSH1YDvOx1MB3aunf41QYwaeFMryMTarzQzA07LJ1TgzLYTWEr6SUSwoMi\n" +
                "0ssazLXNffT568eP2PXskqNLyradzj0ewQKCAQEArIYNvJvhp83HiETMUxwEwK58\n" +
                "u9tgyQ12zA2KcO1pDJXEVpqJeMYtBuEqxeiNjycdjjcRJdSE0qal4M7bHPoW32FO\n" +
                "oWMtjenSbMFAu1IsCBgl/dJEkhjCe93PGXhQpg8BzuEgV3nyrZ8mEUFMBo8A3EDs\n" +
                "xd2KcBM8mUHxMb3wVZI/eRg5aXByUTbBAQRSVqY2GVWOnc9LFU6oUtr7X1JX6k3S\n" +
                "PQI0aBrlONtZzIwnM5wzOp930Cd5wAo6BydnWqkAUEc9GM051DPFSe29K9/tlhHz\n" +
                "OwbEHA/s07iVItVG56laD9JAgMoxTxQORv63e7eoa+YeXlOmlVu2EKo5AEdIEQ==\n" +
                "-----END RSA PRIVATE KEY-----\n", //TODO divibib001.pem
        supportForRefreshToken = false,
    )
)

//Keycloak Switch with private_JWT
fun createkeycloakJWTLibrary() =
createLibrary(
    id = "test-switch-dotsource",
    loginMethod = SwitchOpenIdLoginMethod(
        authorizationEndpoint = "https://keycloak-staging.onleihe.de/realms/test/protocol/openid-connect/auth",
        tokenEndpoint = "https://keycloak-staging.onleihe.de/realms/test/protocol/openid-connect/token",
        userInfoEndpoint = "https://keycloak-staging.onleihe.de/realms/test/protocol/openid-connect/userinfo",
        clientId = "dotsourcePrivateKeyJWT",
        scope = "openid",
        privateKey = "-----BEGIN RSA PRIVATE KEY-----\n" +
                "MIIJKAIBAAKCAgEA8p69+UmLpXqkeyFYlZJdRibFui5NPVfVmBRiImayEHHxQlWh\n" +
                "O0SxiiEOYzrs5rPwMk0kaIu7uIhMq5o5DYJtiFo8iFnwmAg18XJrFI3V5blxA/5E\n" +
                "lGXjbZy9llMK9691ku+E+QU0MhaGoudKP0RkZb58q30Aa35PHImuxaKmagrigth4\n" +
                "1QBqlcHRipzRU+p+uwRjxRKYuvfc8gv2T5OawW3ARHxy04gguGZyaHMYsoSo1fW6\n" +
                "pZHsGU49RTzZfvx8Galvll8XjG+xyanVPpBRxx6U3xHd+yXFgtO3lcuHDmQDCYZ4\n" +
                "jw+b0z+VS1eN+XalQMzlwKA7zb4X3os/FTnKUS3mnJtxsn1XkD+PKKZ+1J4ikSDp\n" +
                "ljNu5VyaFvU3dArWDJzTlAUJ2gPA+xp6Uhe/ZlqGZmp9zFHF4eBB9yeRwz/TAHFO\n" +
                "e3INXTS9ONJcZETF9dCPbjwlDV15otwKxoA+ASfI8nAxG9a0jwHsnsqZKEp65/up\n" +
                "iPJ55CI0vbs/6ElJWRQDQpSir3lJAc3jTkd+5xoa+HP/ICGAbZYnYbz7PpFsoM2S\n" +
                "L2dLLx4NFci/LIclr+410ev9gLIIXhAnUMyUDEZWTdN3p5UCAP+pp53aCSO6q4g2\n" +
                "nn2bsH4so07thyLX+AD+w5M0xUhWTS8Uh1ejsZdMNrtIi3py5dcKLOpvogsCAwEA\n" +
                "AQKCAgAG05iczwjy2wSNwl5vtvKQ6r5dgrgBpdsIsGoidalE9S/IoggrVr94F34E\n" +
                "JMBNrXpwr+L6XhQmGQ+vNGOukaDrWT6ix4URggmihYmlxmfiUtlvN1ROqJ9Bkktz\n" +
                "U1rXpUQ1oi+nohHZ5vBa3Q8lwJLimf2ABcBhReiL8QPN48my6iN9cwHTXHOjSxTO\n" +
                "RaNOLSbrtC/qd598g0TgOp3vl0OfdasXQjko4oepKJjHf5WOUFU/z+40gmqWO7FV\n" +
                "pWSpQglr32yYWoybEvl5pyGs5PqoE6A0tpf8XQ+1oqdju5pNOVsshXAW9mV4CC33\n" +
                "NLt17XSw4hME3H1SSKr7RAe+XSXoHNAv88cmC8boiz+uvptEsqAxHQatPeUcuweU\n" +
                "kSLknTPDpGnO0Nj6Wbt98UQRZ1NNoX/+zLszlJ0oUOKsi9fUZ8VGZgBIatU+cRIL\n" +
                "Eudl0H1fJptCRa9cdDCE+UWhus/P8NcPpCNdNvciPLEZLZtgCO9phwCSdyMv1xyB\n" +
                "5u8UJGY81wzybXHnOzhM+d2vsgntbQYuG2PncDo1V7qu4WXsbr2E3wMY6q6zPnod\n" +
                "ac5pVHHfXNk3C+yDkRn5f4vLZqziXtccNBU3IKsKQTFwoTgpAK7m3jSLxadAXPTR\n" +
                "dOoLJ3AfFWgezn+s4qmZydeNhEo5ozOPZPyiEQ/hbLzyvKfp4QKCAQEA+UIUnIJI\n" +
                "MjNjTvrW71i0gXpPpfxVUXARNCXQ7gM1AlefSBAFL9LNHQicLHvW/osF8owwjJ5Q\n" +
                "GOjckNKOZ/PnM8clKmG0VtHZto2UmkNagCA7F9Lwz2K32bKzpjh//ztHFmGnxo43\n" +
                "UKZxtZ9duG+05Dr4gWm07CSStFKeVraveHZwTk5sAlOpJUEaUTMXGuYF6+Z2ZA+/\n" +
                "k3xBxmdNgu+BvPHLPR/1QO3ETI+KrK7g+u5/nSEAoXooHis4Lgqqcngez1fAPnZ4\n" +
                "nQsv5fGEQOjAnuKYuaqI67kDrcxXnvBZTIwNf9VoShiaiKfmOKfxBoIw23dE73sg\n" +
                "k39olqw27VZWFQKCAQEA+S6ywoffnTIDjAk2vhasV/PQlQ5B6f+TKqHdh46E/VXF\n" +
                "mfoV9gHZ/cmhdlKEn34ZxQtiMORwlg3CY5EVqD3BcovVROMAbTA6+xCGGcSH1xXz\n" +
                "7AvHJpAPDVwnDF3MRe7on7Pbtf3GxMHekl0ZRJ5k6wvzB5AlJBdDyFnuDBrXtUh2\n" +
                "wQQK/oySHJB8ArRw9w/KjuJxL08Ny212go7ZWiR4/U1aNe8uy+beKQqbx64f1i7i\n" +
                "wBBjvGnfS3V/ohI+uHkTQ7MpvMpRIvpMWRFKa9OLCiH+G0MQREboNCs0rdZMLHiL\n" +
                "45APZjJ3Dr7/wF9ZQRAFQX8Q0VoLJxjnyTNGSV0/nwKCAQBLiagfaigg9ovRApmi\n" +
                "fVpuprd+kQL2JCVCjXdMujIVXKKLvzcjMBozA0n4/SrBhzYtNCj+ZHfXpLI7PWQk\n" +
                "wri9a0urFWMGsJ/u/+cZ/PFvRNVDugx6fOtEkTB6XkMEAV3gvYWP2cePrHFnXj2T\n" +
                "yF6kj+6BoZxPuCGxfhw4ITL6KcKNKJz484ueG6WzF+2XnEANJpGYb2Fae9yfGO1X\n" +
                "kefab9hs1MOJgsTrgtC+1lyeDysjXj4HsbJ4uhZTda4D9L05jtlW1KZmyV4hrFit\n" +
                "JxxAymEIW1MVRMOFpxiitSoK1aK9q9noIp6qbV+KqfKUS14tFG8SuFi9+m2iw9v9\n" +
                "fx2lAoIBADSRl4DWrWQ6YukuIHBl1CIR+9UWVu2Iz4FU+KladdAEQlXDntJxfY2+\n" +
                "9eNCcVw8fC9HwYVTDDV73aCnwQvnS2JhAbMwuKxQWoEQ8VmKTqYfqYAI1BDcn3BQ\n" +
                "GqTPPQMb7dOTXCy+xypHRC9YmLYTPHMh6DmWPV+p0ND5t/Cx0QQbr4H02mTtPv3r\n" +
                "tV3Ut2hH2pHeT8aNRPm/tBrDDDOqMiQuJ3Pta8/0erXp2Qd9QpYq+gdO7duX8lW3\n" +
                "CiAk/TppwJituYZsoEVizKyVqz+oN1MB7NvPgCUHWIF5A/oQla1kGQ7/G2CSEO/5\n" +
                "d3ccJnZFbPZuWhvwhR3QcUXPHWro0z0CggEBAOhAJcP33vnpoaiH9OYktlAuxWs8\n" +
                "jMVJ3alFJAhyodtwoNuEb17F+6YnrB6DrBf+Oy6ymjaerExiZVOSMigzOIXJaevT\n" +
                "s9fHvM/blBkOBAk6Lchs6ud7pDjLB8WGkWauQHJBPyd7+eEoDaXnnj+Zt5Gw8Y/t\n" +
                "x0m10kKfOpwkcODQhiv/e/da64k6155d4V/Om1FZ8ZSDToVCEmReVqlrLjyZcLat\n" +
                "FEuj3jAxJ1PGrzDDZwrMKajeiyrL86SEtV5bwfvR+FhcaqzUkf2axgjlRYDrkpy8\n" +
                "Q0d1xLpLufRIcX2srH1EfaORycHAWazGZpD1d6mpOprjSLaWZyRUrqk7Oko=\n" +
                "-----END RSA PRIVATE KEY-----\n",//TODO dostsourceTest.pem
        supportForRefreshToken = false,
    )
),
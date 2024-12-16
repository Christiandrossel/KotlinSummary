XL Licence

LicenceEvent

````kotlin
import javax.accessibility.AccessibleState.ACTIVE
import javax.print.attribute.standard.Media

LicenceEvent(
    divibibLibraryId = 3002,
    divibibLicenceId = 42373309,
    licenceState = ACTIVE,
    licenceModel = XL_SINGLE_CLASS_MEDIA_LICENCE,
    amount = 1,
    contingentRestriction = ContingentRestriction(contingent = 20, maxNumberOfActiveLends = 2),
    readingBasedRestrictions = ReadingBasedRestrictions(
        isPrintingLowResolutionAllowed = false,
        isCopyingAllowed = false,
        isPrintingAllowed = false
    ),
    timeBasedRestrictions = TimeBasedRestrictions(startDate = 2024 - 10 - 22 T22 :00:
    0
    0 Z,
    endDate = 2028 - 10 - 21 T22 :
    0
    0:00 Z, maxLendingDuration = null, startDateUnlimitedUse = null), subscriptionRestrictions =
    null, serialBundleRestrictions = null, mediaList = [Media(
    productIdentifierList = [ProductIdentifierEvent(
        identifierType = ISBN_13,
        identifier = 9783552056206
    ), ProductIdentifierEvent(
        identifierType = EAN,
        identifier = 9783552056206
    ), ProductIdentifierEvent(identifierType = GR_ID, identifier = 5e b3ae9b352df30001b81647)],
    supplier = Supplier(
        id = 35184232,
        datasourceId = 65 c2420c673f3d10bd629307,
        displayName = carlhanser,
        companyName = Carl Hanser Verlag
    ),
    dvbMediaVersionId = 358788271
)], packageInfo = PackageInfo(divibibPackageId = 1956374534, goldenRecordId = 5e b3ae9b352df30001b81647), lends =
    [], stock = [Stock(
    packageLicense2LibraryId = 42373309,
    amount = 0,
    productAmounts = [ProductAmount(
        productIdentifiers = [ProductIdentifierEvent(
            identifierType = ISBN_13,
            identifier = 9783552056206
        ), ProductIdentifierEvent(identifierType = EAN, identifier = 9783552056206), ProductIdentifierEvent(
            identifierType = GR_ID,
            identifier = 5e b3ae9b352df30001b81647
        )], availableAmount = 0
    )]
)], key = 42373309, timestamp = 2024 - 11 - 29 T08 :43:40.472480800 Z)
````

Response

````json
[
  {
    "divibibLibraryId": "3002",
    "divibibLicenceId": "42373309",
    "licenceState": "ACTIVE",
    "licenceModel": "XL_SINGLE_CLASS_MEDIA_LICENCE",
    "amount": 1,
    "contingentRestriction": {
      "contingent": 20,
      "maxNumberOfActiveLends": 2
    },
    "readingBasedRestrictions": {
      "isPrintingLowResolutionAllowed": false,
      "isCopyingAllowed": false,
      "isPrintingAllowed": false
    },
    "timeBasedRestrictions": {
      "startDate": "2024-10-22T22:00:00Z",
      "endDate": "2028-10-21T22:00:00Z",
      "maxLendingDuration": null,
      "startDateUnlimitedUse": null
    },
    "subscriptionRestrictions": null,
    "serialBundleRestrictions": null,
    "mediaList": [
      {
        "productIdentifierList": [
          {
            "identifierType": "ISBN_13",
            "identifier": "9783552056206",
            "isRelevantTypeForIdentification": true
          },
          {
            "identifierType": "EAN",
            "identifier": "9783552056206",
            "isRelevantTypeForIdentification": true
          },
          {
            "identifierType": "GR_ID",
            "identifier": "5eb3ae9b352df30001b81647",
            "isRelevantTypeForIdentification": true
          }
        ],
        "supplier": {
          "id": "35184232",
          "datasourceId": "65c2420c673f3d10bd629307",
          "displayName": "carlhanser",
          "companyName": "Carl Hanser Verlag"
        },
        "dvbMediaVersionId": 358788271
      }
    ],
    "packageInfo": {
      "divibibPackageId": 1956374534,
      "goldenRecordId": "5eb3ae9b352df30001b81647"
    },
    "lends": [],
    "stock": [
      {
        "packageLicense2LibraryId": 42373309,
        "amount": 0,
        "productAmounts": [
          {
            "productIdentifiers": [
              {
                "identifierType": "ISBN_13",
                "identifier": "9783552056206",
                "isRelevantTypeForIdentification": true
              },
              {
                "identifierType": "EAN",
                "identifier": "9783552056206",
                "isRelevantTypeForIdentification": true
              },
              {
                "identifierType": "GR_ID",
                "identifier": "5eb3ae9b352df30001b81647",
                "isRelevantTypeForIdentification": true
              }
            ],
            "availableAmount": 0
          }
        ]
      }
    ],
    "key": "42373309",
    "timestamp": "2024-11-29T08:42:35.687904300Z"
  }
]
````

## Licence Respone mit PROD

````json
[
  {
    "divibibLibraryId": "3002",
    "divibibLicenceId": "42373309",
    "licenceState": "ACTIVE",
    "licenceModel": "XL_SINGLE_CLASS_MEDIA_LICENCE",
    "amount": 1,
    "contingentRestriction": {
      "contingent": 20,
      "maxNumberOfActiveLends": 2
    },
    "readingBasedRestrictions": {
      "isPrintingLowResolutionAllowed": false,
      "isCopyingAllowed": false,
      "isPrintingAllowed": false
    },
    "timeBasedRestrictions": {
      "startDate": "2024-10-22T22:00:00Z",
      "endDate": "2028-10-21T22:00:00Z",
      "maxLendingDuration": null,
      "startDateUnlimitedUse": null
    },
    "subscriptionRestrictions": null,
    "serialBundleRestrictions": null,
    "mediaList": [
      {
        "productIdentifierList": [
          {
            "identifierType": "ISBN_13",
            "identifier": "9783552056206",
            "isRelevantTypeForIdentification": true
          },
          {
            "identifierType": "EAN",
            "identifier": "9783552056206",
            "isRelevantTypeForIdentification": true
          },
          {
            "identifierType": "GR_ID",
            "identifier": "5eb3ae9b352df30001b81647",
            "isRelevantTypeForIdentification": true
          }
        ],
        "supplier": {
          "id": "35184232",
          "datasourceId": "65c2420c673f3d10bd629307",
          "displayName": "carlhanser",
          "companyName": "Carl Hanser Verlag"
        },
        "dvbMediaVersionId": 358788271
      }
    ],
    "packageInfo": {
      "divibibPackageId": 1956374534,
      "goldenRecordId": "5eb3ae9b352df30001b81647"
    },
    "lends": [],
    "stock": [
      {
        "packageLicense2LibraryId": 42373309,
        "amount": 0,
        "productAmounts": [
          {
            "productIdentifiers": [
              {
                "identifierType": "ISBN_13",
                "identifier": "9783552056206",
                "isRelevantTypeForIdentification": true
              },
              {
                "identifierType": "EAN",
                "identifier": "9783552056206",
                "isRelevantTypeForIdentification": true
              },
              {
                "identifierType": "GR_ID",
                "identifier": "5eb3ae9b352df30001b81647",
                "isRelevantTypeForIdentification": true
              }
            ],
            "availableAmount": 0
          }
        ]
      }
    ],
    "key": "42373309",
    "timestamp": "2024-11-29T09:14:03.570993200Z"
  }
]
````

# Gefundende User

````log
Patron(id=67333a4a0ba833049953c6fe, externalIds=ExternalIdentification(upaUserHandle=02A1A2434B4F232CF5816D91D0E7B109, subject=null), onleiheUserName=null, onleiheId=670f876320922f2e834bfc73, libraryId=670f877120922f2e834bfccc, ereaderCode=gjqA, profileList=[Profile(id=master, masterProfile=true, fsk=12, progressList=[])], reservationList=[], wishlists=[Wishlist(id=default, profileId=master, name=Wunschliste, productList=[])], lendList=[LendItem(userId=67333a4a0ba833049953c6fe, profileId=master, productId=6709313953e7dc00703ce09a, lendId=674628de0fadaa2d128b0436, startDate=2024-11-26T20:00:30.371Z, endDate=2024-12-17T20:00:30.371Z, assets=[AssetInfo(assetId=81102430-6a5a-42b8-8830-9a48c954ea78, type=EPUB), AssetInfo(assetId=5f98df3d42f2dc150ed1db85-65c24241673f3d10bd6297e7, type=EPUB)], mediaType=E_BOOK)], ratingList=[], acceptedLegalDocuments=[LegalDocumentItem(legalDocumentId=671240e5b889071638f58e67), LegalDocumentItem(legalDocumentId=old-general-data-protection-regulation), LegalDocumentItem(legalDocumentId=67124035b889071638f58e66)], informedLegalDocuments=[], createdAt=null, updated=2024-11-26T20:00:30.913Z) for userhandle 02A1A2434B4F232CF5816D91D0E7B109
Patron(id=67333a7fae57dc435ad019fa, externalIds=ExternalIdentification(upaUserHandle=4660BCCF8641622A9200D96D53FA4F70, subject=null), onleiheUserName=null, onleiheId=670f876320922f2e834bfc73, libraryId=670f877120922f2e834bfcc9, ereaderCode=2amn, profileList=[Profile(id=master, masterProfile=true, fsk=12, progressList=[])], reservationList=[], wishlists=[Wishlist(id=default, profileId=master, name=Wunschliste, productList=[])], lendList=[], ratingList=[], acceptedLegalDocuments=[], informedLegalDocuments=[], createdAt=null, updated=2024-11-17T23:06:10.725Z) for userhandle 4660BCCF8641622A9200D96D53FA4F70
Patron(id=673751c2ad090d3fc868478a, externalIds=ExternalIdentification(upaUserHandle=586DBEA944441C55DB28F51E3C568116, subject=null), onleiheUserName=0000322, onleiheId=670f876320922f2e834bfc73, libraryId=670f878120922f2e834bfd18, ereaderCode=55xF, profileList=[Profile(id=master, masterProfile=true, fsk=12, progressList=[])], reservationList=[], wishlists=[Wishlist(id=default, profileId=master, name=Wunschliste, productList=[])], lendList=[], ratingList=[], acceptedLegalDocuments=[LegalDocumentItem(legalDocumentId=671240e5b889071638f58e67), LegalDocumentItem(legalDocumentId=old-general-data-protection-regulation), LegalDocumentItem(legalDocumentId=67124035b889071638f58e66)], informedLegalDocuments=[], createdAt=null, updated=2024-11-15T13:51:09.031Z) for userhandle 586DBEA944441C55DB28F51E3C568116
Patron(id=6740608dad090d3fc869213c, externalIds=ExternalIdentification(upaUserHandle=A7BD400442411D74970AED628F58420B, subject=null), onleiheUserName=358, onleiheId=670f876320922f2e834bfc73, libraryId=670f878120922f2e834bfd18, ereaderCode=isnC, profileList=[Profile(id=master, masterProfile=true, fsk=12, progressList=[])], reservationList=[], wishlists=[Wishlist(id=default, profileId=master, name=Wunschliste, productList=[])], lendList=[], ratingList=[], acceptedLegalDocuments=[LegalDocumentItem(legalDocumentId=671240e5b889071638f58e67), LegalDocumentItem(legalDocumentId=old-general-data-protection-regulation), LegalDocumentItem(legalDocumentId=67124035b889071638f58e66)], informedLegalDocuments=[], createdAt=null, updated=2024-11-22T10:44:52.769Z) for userhandle A7BD400442411D74970AED628F58420B
Patron(id=67333493ae57dc435ad0183c, externalIds=ExternalIdentification(upaUserHandle=BAC229A754F9D1507B8934A8E5D47F11, subject=null), onleiheUserName=null, onleiheId=670f876320922f2e834bfc73, libraryId=670f876f20922f2e834bfcbd, ereaderCode=MKef, profileList=[Profile(id=master, masterProfile=true, fsk=12, progressList=[])], reservationList=[], wishlists=[Wishlist(id=default, profileId=master, name=Wunschliste, productList=[])], lendList=[LendItem(userId=67333493ae57dc435ad0183c, profileId=master, productId=670926b5e0c0253d15109f45, lendId=673895a0e331e73ca7bdc6b7, startDate=2024-11-16T12:52:47.998Z, endDate=2024-12-07T12:52:47.998Z, assets=[AssetInfo(assetId=1fa6524b-6add-4bc3-bbcf-3fb765457006, type=EPUB), AssetInfo(assetId=6363335724e48400062fdcbe-65c2424f673f3d10bd6298ea, type=EPUB)], mediaType=E_BOOK)], ratingList=[], acceptedLegalDocuments=[LegalDocumentItem(legalDocumentId=671240e5b889071638f58e67), LegalDocumentItem(legalDocumentId=old-general-data-protection-regulation), LegalDocumentItem(legalDocumentId=67124035b889071638f58e66)], informedLegalDocuments=[], createdAt=null, updated=2024-11-16T12:52:48.552Z) for userhandle BAC229A754F9D1507B8934A8E5D47F11
Patron(id=673332670ba833049953c421, externalIds=ExternalIdentification(upaUserHandle=D7ACC2E490B3E0BBB512E28D43099AFB, subject=null), onleiheUserName=null, onleiheId=670f876320922f2e834bfc73, libraryId=670f876920922f2e834bfca6, ereaderCode=HBXT, profileList=[Profile(id=master, masterProfile=true, fsk=12, progressList=[])], reservationList=[], wishlists=[Wishlist(id=default, profileId=master, name=Wunschliste, productList=[WishlistItem(profileId=master, productId=670933bbe0c0253d1512202e, isPinned=false), WishlistItem(profileId=master, productId=6708fe2e53e7dc00703a6290, isPinned=false), WishlistItem(profileId=master, productId=6708fe28e0c0253d150f1a1b, isPinned=false), WishlistItem(profileId=master, productId=67090b1353e7dc00703ae6d0, isPinned=false), WishlistItem(profileId=master, productId=6709307ae0c0253d15117a4a, isPinned=false), WishlistItem(profileId=master, productId=6709456ee0c0253d1516052d, isPinned=false), WishlistItem(profileId=master, productId=6709419453e7dc0070406fde, isPinned=false), WishlistItem(profileId=master, productId=6709416453e7dc0070406563, isPinned=false), WishlistItem(profileId=master, productId=670924a053e7dc00703bcc37, isPinned=false), WishlistItem(profileId=master, productId=6709410ae0c0253d15150a3f, isPinned=false), WishlistItem(profileId=master, productId=67090b1de0c0253d150f9fa5, isPinned=false), WishlistItem(profileId=master, productId=670909d553e7dc00703adcaf, isPinned=false), WishlistItem(profileId=master, productId=6709099de0c0253d150f93c2, isPinned=false), WishlistItem(profileId=master, productId=67090286e0c0253d150f50a2, isPinned=false), WishlistItem(profileId=master, productId=67093ca3e0c0253d1513fe1d, isPinned=false), WishlistItem(profileId=master, productId=67090ba5e0c0253d150fa41f, isPinned=false), WishlistItem(profileId=master, productId=6709031a53e7dc00703a9d60, isPinned=false), WishlistItem(profileId=master, productId=6708fd8ee0c0253d150f1194, isPinned=false), WishlistItem(profileId=master, productId=6709035353e7dc00703a9fbd, isPinned=false), WishlistItem(profileId=master, productId=67090a93e0c0253d150f9ba6, isPinned=false), WishlistItem(profileId=master, productId=6709095153e7dc00703ad860, isPinned=false), WishlistItem(profileId=master, productId=67094128e0c0253d15151133, isPinned=false), WishlistItem(profileId=master, productId=6708fdfce0c0253d150f1791, isPinned=false)])], lendList=[LendItem(userId=673332670ba833049953c421, profileId=master, productId=6709116253e7dc00703b193e, lendId=67374804e331e73ca7bd8007, startDate=2024-11-15T13:09:24.592Z, endDate=2024-12-06T13:09:24.592Z, assets=[AssetInfo(assetId=341b00a2-c42d-47ac-89c3-5d1c56fb005b, type=EPUB), AssetInfo(assetId=5eb3d79e352df30001bc0cf0-65c24312673f3d10bd629cf8, type=EPUB)], mediaType=E_BOOK), LendItem(userId=673332670ba833049953c421, profileId=master, productId=6708fdfce0c0253d150f1791, lendId=673b2dd3e331e73ca7be5284, startDate=2024-11-18T12:06:42.960Z, endDate=2024-12-02T12:06:42.960Z, assets=[AssetInfo(assetId=30748f32859ece3b7236d58f8f83a61a7160352812fe5215a107a5eef28f15fa, type=MP4)], mediaType=E_AUDIO), LendItem(userId=673332670ba833049953c421, profileId=master, productId=67090359e0c0253d150f598e, lendId=673debb4e331e73ca7c1361d, startDate=2024-11-20T14:01:24.132Z, endDate=2024-12-04T14:01:24.132Z, assets=[AssetInfo(assetId=5eb3924e352df30001b58311_1717175942271, type=MP4)], mediaType=E_AUDIO), LendItem(userId=673332670ba833049953c421, profileId=master, productId=67090a93e0c0253d150f9ba6, lendId=6740829e990cd45cab3b826a, startDate=2024-11-22T13:09:50.117Z, endDate=2024-12-06T13:09:50.117Z, assets=[AssetInfo(assetId=b211030cf11851a5b289a742977f501e18b575b0b9fb0aa7164c00392a8949cc, type=MP4)], mediaType=E_AUDIO), LendItem(userId=673332670ba833049953c421, profileId=master, productId=6708fe28e0c0253d150f1a1b, lendId=6743054a990cd45cab3c0670, startDate=2024-11-24T10:51:54.359Z, endDate=2024-12-08T10:51:54.359Z, assets=[AssetInfo(assetId=783fbfd01b32c93769da7c409cc84d6e699f4808cd4f0444f896349ee34bfbf5, type=MP4)], mediaType=E_AUDIO)], ratingList=[], acceptedLegalDocuments=[LegalDocumentItem(legalDocumentId=671240e5b889071638f58e67), LegalDocumentItem(legalDocumentId=old-general-data-protection-regulation), LegalDocumentItem(legalDocumentId=67124035b889071638f58e66)], informedLegalDocuments=[], createdAt=null, updated=2024-11-24T10:51:54.704Z) for userhandle D7ACC2E490B3E0BBB512E28D43099AFB
Patron(id=67374c4aad090d3fc86846f2, externalIds=ExternalIdentification(upaUserHandle=D7C3EAA8447D0C4D23D4D4C4BCA44882, subject=null), onleiheUserName=0000619, onleiheId=670f876320922f2e834bfc73, libraryId=670f878120922f2e834bfd18, ereaderCode=RKnk, profileList=[Profile(id=master, masterProfile=true, fsk=12, progressList=[])], reservationList=[], wishlists=[Wishlist(id=default, profileId=master, name=Wunschliste, productList=[])], lendList=[], ratingList=[], acceptedLegalDocuments=[LegalDocumentItem(legalDocumentId=671240e5b889071638f58e67), LegalDocumentItem(legalDocumentId=old-general-data-protection-regulation), LegalDocumentItem(legalDocumentId=67124035b889071638f58e66)], informedLegalDocuments=[], createdAt=null, updated=2024-11-15T13:27:48.957Z) for userhandle D7C3EAA8447D0C4D23D4D4C4BCA44882
Patron(id=673733e5dc6edd466dbe0c97, externalIds=ExternalIdentification(upaUserHandle=E991A6A274F6C43835102264C7C43B24, subject=null), onleiheUserName=589, onleiheId=670f876320922f2e834bfc73, libraryId=670f878120922f2e834bfd18, ereaderCode=CMet, profileList=[Profile(id=master, masterProfile=true, fsk=12, progressList=[])], reservationList=[], wishlists=[Wishlist(id=default, profileId=master, name=Wunschliste, productList=[])], lendList=[LendItem(userId=673733e5dc6edd466dbe0c97, profileId=master, productId=6709368ae0c0253d1512ad15, lendId=673737ae25f0bf5688fc0a60, startDate=2024-11-15T11:59:42.559Z, endDate=2024-12-06T11:59:42.559Z, assets=[AssetInfo(assetId=07549237-0c41-4db6-b47d-157c82ec474a, type=EPUB), AssetInfo(assetId=65cb3a9885cb00287028fa34-65c24244673f3d10bd629870, type=EPUB)], mediaType=E_BOOK), LendItem(userId=673733e5dc6edd466dbe0c97, profileId=master, productId=67093315e0c0253d1511fefa, lendId=67373a6025f0bf5688fc0acd, startDate=2024-11-15T12:11:12.467Z, endDate=2024-11-29T12:11:12.467Z, assets=[AssetInfo(assetId=2e22195f9968f4a65cdcaadc4813244c4a6d0bd02982035a94e33c3a5c726ba8, type=MP4)], mediaType=E_AUDIO)], ratingList=[], acceptedLegalDocuments=[LegalDocumentItem(legalDocumentId=671240e5b889071638f58e67), LegalDocumentItem(legalDocumentId=old-general-data-protection-regulation), LegalDocumentItem(legalDocumentId=67124035b889071638f58e66)], informedLegalDocuments=[], createdAt=null, updated=2024-11-17T10:35:43.280Z) for userhandle E991A6A274F6C43835102264C7C43B24

````

# Licence Import Statistic:

````
Licence-Import-Preview for library 3002: Import-Statistics (in ms):
ImportStatistics(libraryId=3002, targetEnvironment=PROD, importType=LICENCES_PREVIEW, startTime=2024-11-29T09:13:52.245547500Z, endTime=2024-11-29T09:14:05.105949800Z, licenseCount=1, licenseEventsCount=0, reservationsCount=0, reservationEventsCount=0, userCreatedCount=0, userFoundCount=8, userCountTotal=8, lendCount=0, stockCount=1, totalTimeMs=12860, userImportMs=3924, licenseRetrievalMs=209, sumSupplierEnrichmentMs=93, sumNumberCodeEnrichmentMs=106, sumRestrictionEnrichmentMs=74, sumLendEnrichmentMs=63, sumStockEnrichmentMs=144, failures={})
Enrichment averages:
Supplier: 93
NumberCodes: 106
Restrictions: 74
Lends: 63
Stock: 144
````

# Vergleich

* Vergleich der XL Lizenzen in der DB und im MigrationsService

|           | Kontingent | parallele Ausleihen | bereits ausgeliehen | stock.amount | availableAmount |
|-----------|------------|---------------------|---------------------|--------------|-----------------|
| Ticket    | 20         | 2                   | 20                  |              |                 |
| Response  | 20         | 2                   | 0                   | 0            | 0               |
| In der DB | 20         | 2                   | 9                   |              |                 |

### Ticket

* Hier wird beschrieben das, dass Kontingent vo 20 bereits aufgebraucht wurde
* Bei der XL Lizenz müsste also nur noch parallel 2 Ausleihen möglich sein

### Response

* Ist die Preview aus dem MigrationsService
* Allerdings kam ein Fehler zu den Nutzern (Failed to resolve O3-library for O2-library-id 3002, will abort import of
  users)
* Die Nutzer werden benötigt, um die Ausleihen zu prüfen und anzulegen
* D.h. ich weiß nicht wie das verbleibende Kontingent ist
* -> TODO muss ich noch Inventory Service prüfen
* -> Hinweis: stock.amount ist 0, d.h. kein verbleibendes Kontingent
* -> Hinweis: availableAmount ist 0, d.h. kein verbleibendes Kontingent

### In der DB

* Das bezieht sich auf den Stand vom 26.11.2024
* Hier sind zu dem zeitpunkt 20-9=11 Ausleihen offen


# Analyse Inventory-Service
* Der Inventory Service hat bereits eine Implementierung die das verbleibende Kontingent anpasst

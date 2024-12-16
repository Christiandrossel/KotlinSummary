# Analyse XL Licence

## Ticket:
Link zum Ticket: https://ekz-gruppe.atlassian.net/browse/OB3-1774

Lange Fassung:

Aus dem Chat mit Jan N.
Migration XL-Lizenzen fehlerhaft?
Bei eMedienBayern ist @smuel etwas Kurioses aufgefallen.
Dieses Buch wird mit 17 verfügbaren Exemplaren angezeigt: Onleihe 3.0
Im Backend sind dazu zwei Lizenzen zu sehen: Backoffice  Eine einfache M-Lizenz, eine XL-Lizenz mit 20 parallelen, danach 2 seriellen Ausleihen.
Das passt zu den Käufen aus dem O2-System.
Allerdings gibt der O2-License Service aus, dass beide Lizenzen im "M"-Teil ihres Lebenszyklus sind:
M-Lizenz vom 16.10. http://license.service.user.dvb/rest/v2/licenses?licenseId=42362923&documentId=358788271 (3 Ausleihen insgesamt, aktuell 1 verfügbar) => Angezeigter Lizenztyp "M"
XL-Lizenz vom 23.10. http://license.service.user.dvb/rest/v2/licenses?licenseId=42373309&documentId=358788271 (24 Ausleihen insgesamt, aktuell 2 verfügbar) => Angezeigter Lizenztyp ebenfalls "M", weil alle 20 parallelen XL-Leihen eben schon aufgebraucht sind.
Im O2-System wären also 3 Exemplare verfügbar (1x M plus 2 x parallel ausleihbar aus der XL-Lizenz).
Vormerker zu beiden Lizenzen sind im O2-System aktuell 8 eingetragen.
Offenbar wird beim Import in die O3 nicht richtig berücksichtigt, dass der Parallel-Part der XL-Lizenz schon aufgebraucht ist. Die XL-Lizenz wird dagegen einfach als 20 Parallel-Ausleihen importiert, von denen schon 5 Ausleihen aufgebraucht sind.
Wie sich dann aus insgesamt (20 - 5 = 15 ) + 1 Exemplaren am Ende 17 ergeben, kann ich nicht nachvollziehen. Falsch ist auf jeden Fall, die XL-Lizenz mit 20 verfügbaren Exemplaren zu importieren. RIchtiger wäre eine serielle Lizenz mit 2 Exemplaren

Zusammenfassung:
XL Lizenz hat ein Kontingent von 20 und eine maximale Anzahl von 2 parallelen Ausleihen
20 parallele Ausleihen wurden bereits aufgebraucht


## Licence Respone
* Migration Service Preview der Licence:
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



# Vergleich
* Vergleich der XL Lizenzen us dem Ticket, in der DB und im MigrationsService

|           | Kontingent | parallele Ausleihen | bereits ausgeliehen |
|-----------|------------|---------------------|---------------------|
| Ticket    | 20         | 2                   | 20                  |
| Response  | 20         | 2                   | 0                   |
| In der DB | 20         | 2                   | 9                   |

### Ticket
* Hier wird beschrieben das, dass Kontingent vo 20 bereits aufgebraucht wurde
* Bei der XL Lizenz müsste also nur noch parallel 2 Ausleihen möglich sein
* O2 Ids:
  * external library id: 3002
  * external licence id: 42373309

### Response
* Ist die Preview aus dem MigrationsService
* Zu der Licence wurden keine Lends gefunden, die Liste an Lends ist leer
* Annahme, damit gibt es kein verbrauchtes Kontingent
* -> TODO muss ich noch Inventory Service prüfen

### In der DB
* Das bezieht sich auf den Stand vom 26.11.2024
* Hier sind zu dem Zeitpunkt (Kontingent - Ausleihen = verbleibendes Kontingent) 20-9=11 Ausleihen offen
* IDs:
  * XL Licence:  67197250e13a4031b2f78d66
  * Product:  670904ffe0c0253d150f6a54
  * Onleihe: 670f876320922f2e834bfc73



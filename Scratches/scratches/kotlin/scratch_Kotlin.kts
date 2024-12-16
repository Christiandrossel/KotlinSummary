/**
 * Differents List and Set
 */
// Eine Liste ist eine geordnete Sammlung von Elementen, die Duplikate zulässt.
val list: List<Int> = listOf(1, 2, 3, 3, 4, 4, 5)
println(list)

// Ein Set ist eine Sammlung von Elementen, die keine Duplikate zulässt.
val set: Set<Int> = setOf(1, 2, 3, 3, 4, 4, 5)
println(set)


/**
 * convert this to a json
 */

//LicenceInventoryEvent(
//    licence = net.avgl.ekz.onleihe.inventoryevents.inventoryevents.licence.BasicLicence
//    @30 c960fe,
//    key = Castlabs - Parallel - Licence,
//    timestamp = 2023 - 12 - 12 T13 :
//    42:40.663351500 Z, licenceId = Castlabs - Parallel - Licence, onleiheId = onleihe - 1)
{
    "licence": {
    "licenceId": "Castlabs-Parallel-Licence",
    "onleiheId": "onleihe-1",
    "totalLends": 0,
    "contingent": 1,
    "remainingContingent": 1,
    "maxNumberOfReservations": null,
    "maxNumberOfActiveLends": null,
    "startDate": null,
    "endDate": null,
    "maxLendingDuration": null,
    "startDateUnlimitedUse": null,
    "licenceOwnerId": "Castlabs",
    "productId": "Castlabs-Parallel-Licence",
    "activeLends": 0,
    "activeReservations": 0,
    "licenceModel": "Basic",
    "state": "ACTIVE",
    "timestamp": "2023-12-12T13:42:40.663351500Z"
},
    "key": "Castlabs-Parallel-Licence",
    "timestamp": "2023-12-12T13:42:40.663351500Z",
    "licenceId": "Castlabs-Parallel-Licence",
    "onleiheId": "onleihe-1"
}

LicenceInventoryEvent(licence=net.avgl.ekz.onleihe.inventoryevents.inventoryevents.licence.BasicLicence@73f0574e, key=Castlabs-Simple-Licence, timestamp=2023-12-12T13:45:10.512807100Z, licenceId=Castlabs-Simple-Licence, onleiheId=onleihe-1)
{
    "licence": {
    "licenceId": "Castlabs-Simple-Licence",
    "onleiheId": "onleihe-1",
    "totalLends": 0,
    "contingent": 1,
    "remainingContingent": 1,
    "maxNumberOfReservations": null,
    "maxNumberOfActiveLends": null,
    "startDate": null,
    "endDate": null,
    "maxLendingDuration": null,
    "startDateUnlimitedUse": null,
    "licenceOwnerId": "Castlabs",
    "productId": "Castlabs-Simple-Licence",
    "activeLends": 0,
    "activeReservations": 0,
    "licenceModel": "Basic",
    "state": "ACTIVE",
    "timestamp": "2023-12-12T13:45:10.512807100Z"
},
    "key": "Castlabs-Simple-Licence",
    "timestamp": "2023-12-12T13:45:10.512807100Z",
    "licenceId": "Castlabs-Simple-Licence",
    "onleiheId": "onleihe-1"
}
/**
 * Find all Reservations with state READY and lookup to collection licence. Find all licence wehre the activeReservations array contains the reservation id.
 */

/**
 * Licence Example:
 * {
 *   "_id": {
 *     "$oid": "671658f5e13a4031b2f5695f"
 *   },
 *   "externalIds": {
 *     "licenceId": "42290781",
 *     "libraryId": "148"
 *   },
 *   "ownerId": "670f7fec20922f2e834bfc6f",
 *   "productReference": {
 *     "productId": "67096588e0c0253d1519a0fa",
 *     "responsibleFor": [
 *       "67096588e0c0253d1519a0fa"
 *     ],
 *     "mainProductId": "67096588e0c0253d1519a0fa",
 *     "_class": "net.avgl.ekz.onleihe.inventoryapplication.licence_service.model.licence.references.BasicProductReference"
 *   },
 *   "suppliers": [
 *     {
 *       "externalId": "355105690",
 *       "datasourceId": "65c24275673f3d10bd629a4f",
 *       "displayName": "bookwire_nat",
 *       "companyName": "Bookwire national"
 *     }
 *   ],
 *   "state": "ACTIVE",
 *   "licenceModel": "TIME_RESTRICTED_MEDIA_LICENCE",
 *   "activeLends": [],
 *   "totalLends": 2,
 *   "activeReservations": [
 *     "67166514e13a4031b2f5bd41"
 *   ],
 *   "timeBasedRestrictions": {
 *     "startDate": {
 *       "$date": "2024-08-21T22:00:00.000Z"
 *     },
 *     "endDate": {
 *       "$date": "2028-08-20T22:00:00.000Z"
 *     }
 *   },
 *   "contingentBasedRestrictions": {
 *     "maxNumberOfActiveLends": 1
 *   },
 *   "readingBasedRestrictions": {
 *     "isPrintingLowResolutionAllowed": false,
 *     "isCopyingAllowed": true,
 *     "isPrintingAllowed": false,
 *     "maxNumberOfParallelDevices": 7
 *   },
 *   "createdAt": {
 *     "$date": "2024-10-21T13:36:53.396Z"
 *   },
 *   "updated": {
 *     "$date": "2024-12-05T08:35:25.984Z"
 *   },
 *   "licenceAvailability": {
 *     "availabilityByProduct": {
 *       "67096588e0c0253d1519a0fa": {
 *         "isUnlimited": false,
 *         "availability": 0,
 *         "isAvailableOrUnlimited": false
 *       }
 *     },
 *     "isUnlimited": false,
 *     "availability": 0,
 *     "isAvailableOrUnlimited": false
 *   },
 *   "id": "671658f5e13a4031b2f5695f",
 *   "_class": "net.avgl.ekz.onleihe.inventoryapplication.licence_service.database.entities.licence.BasicLicenceEntity"
 * }
 */
/**
 * Reservation Example:
 * {
 *   "_id": {
 *     "$oid": "67166514e13a4031b2f5bd41"
 *   },
 *   "externalReservationId": "89247041",
 *   "productId": "67096588e0c0253d1519a0fa",
 *   "onleiheId": "670f7fec20922f2e834bfc6f",
 *   "userId": "671655feae57dc435acea463",
 *   "profileId": "master",
 *   "startDate": {
 *     "$date": "2024-08-24T21:23:12.566Z"
 *   },
 *   "state": "READY",
 *   "automaticAcceptance": false,
 *   "readyPeriod": {
 *     "startDate": {
 *       "$date": "2024-12-05T08:35:25.967Z"
 *     },
 *     "endDate": {
 *       "$date": "2024-12-07T08:35:25.967Z"
 *     }
 *   },
 *   "updated": {
 *     "$date": "2024-12-05T08:35:25.967Z"
 *   },
 *   "_class": "net.avgl.ekz.onleihe.inventoryapplication.licence_service.database.entities.ReservationEntity"
 * }
 */


/**
 * Find all Reservations with state READY and lookup to collection licence.
 * Convert the id to string
 * Find all licence wehre the activeReservations array contains the reservation id.
 * Filter all if the licence is empty.
 */
db.reservation.aggregate([
    {
        $match: {
            state: "READY"
        }
    },
    {
        $addFields: {
            reservationId: {
                $toString: "$_id"
            }
        }
    },
    {
        $lookup: {
            from: "licence",
            localField: "reservationId",
            foreignField: "activeReservations",
            as: "licence"
        }
    },
    {
        $match: {
            licence: {
                $eq: []
            }
        }
    }
]).pretty()

/**
 * Find all Reservations with state READY and lookup to collection licence.
 * Convert the id to string
 * Find all licence wehre the activeReservations array contains the reservation id.
 *
 * Andersrum: Find all licence wehre the activeReservations array contains the reservation id.
 * Find all licences with activeReservations array is not Empty, contains the reservation id, convert to ObjectID and lookup to collection reservation.
 * Filter all reservations with state READY.
 */
db.licence.aggregate(
    [
        {
            $match: {
                activeReservations: {
                    $ne: []
                }
            }
        },
        {
            $match: {
                _id: {
                    $type: "objectId"
                }
            }
        },
        {
            $addFields: {
                activeReservations: {
                    $map: {
                        input: "$activeReservations",
                        as: "reservation",
                        in: {
                            $toObjectId: "$$reservation"
                        }
                    }
                }
            }
        },
        {
            $lookup: {
                from: "reservation",
                localField: "activeReservations",
                foreignField: "_id",
                as: "result"
            }
        },
        // {
        //   $match: {
        //     "result.state": {
        //       $in: ["READY"]
        //     }
        //   }
        // }
        // find all result is empty
        {
            $match: {
                result: {
                    $eq: []
                }
            }
        }
]
).pretty()
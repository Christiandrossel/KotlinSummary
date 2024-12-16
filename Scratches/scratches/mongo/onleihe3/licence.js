/**
 * Ein Mongo Db Query das
 * Suche mir in der Collection licence alle Lizenzen die im array activeLends genauso groß sind wie in contingentBasedRestrictions.maxNumberOfActiveLends
 * Zuerst muss die activeLends welches ein array ist als size umgewandelt werden
 * Beispiel:
 * "activeLends": [
 *     "lend-a01",
 *     "lend-va01"
 *   ],
 *   "totalLends": 2,
 *   "activeReservations": [],
 *   "timeBasedRestrictions": {
 *     "startDate": {
 *       "$date": "2020-01-01T00:00:00.000Z"
 *     },
 *     "endDate": {
 *       "$date": "2025-12-31T23:59:59.999Z"
 *     },
 *     "maxLendingDuration": "PT336H",
 *     "startDateUnlimitedUse": {
 *       "$date": "2026-01-01T00:00:00.000Z"
 *     }
 *   },
 *   "contingentBasedRestrictions": {
 *     "contingent": 100,
 *     "maxNumberOfReservations": 1000,
 *     "maxNumberOfActiveLends": 10
 *   },
 */
db.licences.aggregate([
    {
        $project: {
            activeLends: true,
            sizeOfActiveLends: {$size: "$activeLends"},
            contingentBasedRestrictions: true
        }
    },
    {
        $match: {
            sizeOfActiveLends: "$contingentBasedRestrictions.maxNumberOfActiveLends"
        }
    }
])



db.licences.aggregate([
    {
        $project: {
            activeLends: true,
            contingentBasedRestrictions: true
        }
    },
    {
        // match wenn activeLends existiert und nicht leer ist
        $match: {
            activeLends: {$exists: true, $ne: []},
            // contingentbasedrestrictions.activelends ist ein integer und muss existieren
            "contingentBasedRestrictions.maxNumberOfActiveLends": {$exists: true}
        }
    }
])

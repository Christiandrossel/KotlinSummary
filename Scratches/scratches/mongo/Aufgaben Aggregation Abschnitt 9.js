/**
 * Sortiere raketenstarts nach name
 * ermittle welcher raketentyp wie oft gestartet wurde
 * sortiere absteigend nach Anzahl der Starts
 * gebe die rakete aus, die am häufigsten gestartet wurde
 */
db.launches.aggregate(
    [
        {$group: {_id: "$rocket.name", count: {$sum: 1}}},
        {$sort: {count: -1}},
        {$limit: 1}
    ]
)
// Ergebnis: Falcon 9 --> 137 Starts

db.launches.aggregate([
    {
        $project: {
            name: 1,
            payloadsMass: {
                $sum: {
                    $map: {
                        input: "$payloads",
                        as: "payload",
                        in: "$$payload. mass_kg"
                    }
                }
            }
        }
    },
    {$sort: {"payloadsMass": -1}},
    {$limit: 1}
])

/**
 * gebe nur den namen und die masse der Nutzlasten aus
 * $unwind gibt für jedes element im array ein neues dokument aus
 * gruppiere die masse für jeden raketenstart zusammen
 * sortiere absteigend die massen
 * gebe die rakete aus, die die schwerste nutzlast hatte
 */
db.launches.aggregate(
    [
        {$project: {name: true, "payloads.mass_kg": true}},
        {$unwind: "$payloads"},
        {$group: {_id: "$name", totalMass: {$sum: "$payloads.mass_kg"}}},
        {$sort: {totalMass: -1}},
        {$limit: 1}
    ]
)
// Ergebnis: {
//   _id: 'Starlink-9 (v1.0) & BlackSky Global 5-6',
//   totalMass: 15712
// }

/**
 * Welches Schiff war bei den meisten Raketenstarts beteiligt?
 * Name des schiffes unter ships.name (Array)
 * gebe nur den namen und den ships.name aus
 *
 */
db.launches.aggregate([
    {$project: {name: true, "ships.name": true}},
    {$unwind: "$ships"},
    {$group: {_id: "$ships.name", count: {$sum: 1}}},
    {$sort: {count: -1}},
    {$limit: 1}
])
// Ergebnis: {
//   _id: 'Of Course I Still Love You',
//   count: 51
// }

/**
 * Für welches land wurden die meisten Nutzlasten in den Weltraum gestartet?
 * land einer nutzlast payloads.nationalities (Array)
 * achtung Rakete kann mehrere nutzlasten haben
 * eine nutzlast kann theoretisch für mehrere Länder gestartet wurden sein
 */
db.launches.aggregate([
    {$unwind: "$payloads"},
    {$unwind: "$payloads.nationalities"},
    {$group: {_id: "$payloads.nationalities", count: {$sum: 1}}},
    {$sort: {count: -1}},
    {$limit: 1}
])
// Ergebnis: {
//   _id: 'United States',
//   count: 126
// }


/**
 * welche rakete hat mehr als eine nutzlast gestartet?
 * gib alle einträge heraus dessen payloads (Array) mehr als ein element hat
 */
db.launches.aggregate([
    {$match: {"payloads.1": {$exists: true}}},
    {$project: {name: true, "payloads.nationalities": true}},

])

/**
 * Wie viele Kilogramm (payloads.mass_kg) wurden insgesamt für das Land "United States" gestartet?
 */
db.launches.aggregate([
    {$unwind: "$payloads"},
    {$unwind: "$payloads.nationalities"},
    {$match: {"payloads.nationalities": "United States"}}, // Da in payloads und nationalities unterschiedliche einträge stammen können, muss die match abfrage erst hier  stattfinden
    {$group: {_id: "$payloads.nationalities", totalMass: {$sum: "$payloads.mass_kg"}}}
])

// Ergbenis: {
//   _id: 'United States',
//   totalMass: 706556.7
// }
/**
 * Aufgaben Abschnitt 8 Aggregation Video 85
 * Mongo DB Shell Aggragtion Befehle auf die Collection launches
 */
// um auf die DB zuzugreifen muss vorher "use udemy-mongo" eingegeben werden

/**
 * Aufgabe 1:
 * Wie viele Payloads hat der Raketenstart mit dem Namen STP-2 (Eigenschaft: name) in den Weltraum geschickt?
 */
db.launches.aggregate(
    [
        {$match: {name: "STP-2"}},
        {$project: {name: true, payloads: {$size: "$payloads"}}}
    ]
)
// --> Match 1 Eintrag mit payload Size 16

// findet alle Einträge mit name STP-2 und gruppiert diese und zählt die Anzahl der Einträge
db.launches.aggregate(
    [
        {$match: {name: "STP-2"}},
        {$group: {"_id": null, "count": {$sum: 1}}}
    ]
)
// --> Zeigt Anzahl 1 an

/**
 * Aufgabe 2:
 * Wie viel Kraftstoff kann die Rakete aus Aufgabe 1 insgesamt tanken?
 * Tipp
 * Du findest die Angabe unter rocket.first_stage.fuel_amount_tons für die erste Stufe
 * und unter rocket.second_stage.fuel_amount_tons für die zweite Stufe der Rakete.
 *
 * Diese beiden Werte sollen mit Hilfe des Aggregation-Frameworks aufaddiert werden!
 */
db.launches.aggregate(
    [
        {$match: {name: "STP-2"}},
        {
            $project: {
                name: true,
                "rocket.first_stage.fuel_amount_tons": true,
                "rocket.second_stage.fuel_amount_tons": true,
                totalFuel: {$add: ["$rocket.first_stage.fuel_amount_tons", "$rocket.second_stage.fuel_amount_tons"]}
            }
        }
    ]
)
// --> Ergebnis: 1245 Tonnen; 1155 + 90


/**
 * Aufgabe 3:
 * Space X landet die Rakete ja u.A. auch auf Schiffen
 * Bei wie vielen Raketenstarts waren exakt 5 Schiffe beteiligt?
 * Du findest die Info zu den Schiffen in der Eigenschaft ships
 * Tipp:
 *  Erstelle eine neue Eigenschaft "shipCount"
 *  In dieser wird die Anzahl der Schiffe eingetragen
 *  Schreibe das Ergebnis anschließend in eine neue Collection "launches_ships"
 *  Die Frage kannst du dann mit Hilfe der neuen Collection beantworten
 */
db.launches.aggregate(
    [
        { $match: { ships: {$size: 5} } },
        { $project: { name: true, shipCount: {$size: "$ships"} } },
        { $group: { _id: null, count: {$sum: 1} } }
    ]
)
// Ergebnis: --> 19 Raketenstarts mit 5 Schiffen

// Erstelle eine neue Collection "launches_ships"
db.launches.aggregate(
    [
        { $match: { ships: {$size: 5} } },
        { $project: { name: true, shipCount: {$size: "$ships"} } },
        { $out: "launches_ships" }  // --> erstellt eine neue Collection
    ]
)
// In der neuen Collection werden nur die Eigenschaften name und shipCount gespeichert
// --> db.launches_ships.find({shipCount: 5}).count() --> 19 Schiffe

/**
 * Suche wer ships (ARRAY) nicht leer ist/ Array nicht null
 */
db.launches.aggregate([{$match: {ships: {$ne: []}}}, {$project: {name: true, ships: {$size: "$ships"}}}])
/**
 * Aufgabe 1:
 * ► Wie viele Städte gibt es in Deutschland ("Germany")?
 * ► a) Verwende dazu die Eigenschaft "properties.country" der Collection "cities",
 * und zähle die Anzahl der Einträge
 * ► b) Hole dir aus der "countries"-Collection die Geometry von Deutschland, und
 * filtere nach dieser die Städte
 * ► Stimmen diese beiden Werte überein?
 */
db use udemy-mongo

db.cities.find({"properties.country": "Germany"}).count()

// 2624

var germany = db.countries.findOne({"properties.name": "Germany"});
db.cities.find({geometry: {$geoWithin: {$geometry: germany.geometry}}}).count()

// 2624

/**
 * Aufgabe 2:
 * ► Wie viele Einwohner Deutschlands leben in Städten, die in der
 * Datenbasis erfasst sind?
 * ► Filtere nicht nach der Eigenschaft "properties.country", sondern
 * führe eine $geoWithin-Query aus, bei der du die Geometry von
 * Deutschland übergibst!
 * ► Tipp:
 * ► Du wirst hierfür das Aggregation-Framework benötigen
 * ► Innerhalb eines $match-Schrittes der Aggregation-Pipeline
 * kannst du ein $geoWithin verwenden
 * ► Summiere dazu die Eigenschaft properties.population auf
 * ► Du kannst nach einer Gruppe gruppieren, die für alle Dokumente
 * identisch ist ({$group: {_id: 0,…}})
 */
var germany = db.countries.findOne({"properties.name": "Germany"});
db.cities.aggregate([
    {$match: {geometry: {$geoWithin: {$geometry: germany.geometry}}}},
    {$group: {_id: 0, population: {$sum: "$properties.population"}}}
])

// Auusgabe:
// {
//     _id: 0,
//         population: 67507141
// }

/**
 * Aufgabe 3:
 * ► Wie viele Städte gibt es in einem Umkreis von 100km von Berlin?
 * ► Hinweis, die Koordinaten von Berlin sind:
 * ► [13.3833, 52.5167]
 * ► Tipp:
 * ► Du kannst dies wahlweise mit oder auch ohne das Aggregation
 * Framework lösen
 */
var berlin = [13.3833, 52.5167];
db.cities.find({geometry: {$near: {$geometry: {type: "Point", coordinates: berlin}, $maxDistance: 100000}}}).count()

// 96

/**
 * Mit Aggregation Framework
 */
var berlin = [13.3833, 52.5167];
db.cities.aggregate([
    {$geoNear: {near: {type: "Point", coordinates: berlin}, distanceField: "distance"}},
    {$match: {distance: {$lte: 100000}}}
]).itcount()
//96
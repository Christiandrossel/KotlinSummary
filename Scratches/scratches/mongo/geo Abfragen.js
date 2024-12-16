var austria = db.countries.findOne({"properties.name": "Austria"})

db.cities.find({
    "geometry": {
        $geoWithin: {
            $geometry: austria.geometry
        }
    }
})


// $centerSphere ist für die Berechnung der Distanz
// Der erste Parameter müssen koordinaten sein
// Der zweite Parameter ist der Radius in Kilometer der geteilt durch den Erdradius in Kilometer
db.cities.find({
    "geometry": {
        $geoWithin: {
            $centerSphere: [
                [13.0, 47.0],
                100 / 6378.1
            ]
        }
    }
})


/**
 * Umkreissuche mit der Aggregation-Pipeline
 * $geoNear ist ein Aggregations-Operator, um die Distanz zu berechnen
 * near: Koordinaten die mit type und coordinates angegeben werden
 * type: Typ der Koordinaten (Point)
 * coordinates: Koordinaten von den aus gesucht wird
 * distanceField: Name des Feldes in dem die Distanz ausgegeben werden soll
 * maxDistance: Maximaler Radius in Meter
 * spherical: Berechnung der Distanz auf der Kugeloberfläche
 */
db.cities.aggregate([
    {
        $geoNear: {
            near: {
                type: "Point",
                coordinates: [13.0, 47.0]
            },
            distanceField: "distance",
            maxDistance: 100000,
            spherical: true
        }
    }
])

/**
 * Umkreissuche mit der Aggregation-Pipeline
 * Filter zusätzlich nach der population (properties.population) die größer als 100000 ist
 */
db.cities.aggregate([
    {
        $geoNear: {
            near: {
                type: "Point",
                coordinates: [13.0, 47.0]
            },
            distanceField: "distance",
            maxDistance: 100000,
            spherical: true,
            query: {
                "properties.population": {
                    $gt: 100000
                }
            }
        }
    }
])
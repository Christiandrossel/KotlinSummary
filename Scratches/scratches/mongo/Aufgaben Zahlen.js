/**
 * Aufgabe 1:
 * ► Erstelle für alle Dokumente eine neue Eigenschaft
 * "totalAmount", wo die Einträge von "items.amount"
 * aufsummiert abgespeichert werden
 * ► Wichtig: Verwende hierzu nicht NumberDecimal!
 * ► Tipp:
 * ► Du kannst bei .updateMany() das AggregationFramework verwenden, indem du beim 2. Parameter ein
 * Array übergibst
 * ► Dort kannst du dann z.B. einen $addFields oder einen
 * $project-Schritt ausführen
 * ► db.invoices.updateMany({}, [])
 */
db.invoices.updateMany({}, [
    {
        $addFields: {
            totalAmount: {
                $sum: "$items.amount"
            }
        }
    }
])

db.invoices.aggregate([
    {
        $addFields: {
            totalAmount: {
                $sum: "$items.amount"
            }
        }
    }
])

/**
 * Aufgabe 2:
 * ► Erstelle für alle Dokumente eine neue Eigenschaft
 * "totalAmountDecimal", wo die Einträge von "items.amount"
 * aufsummiert abgespeichert werden
 * ► Wichtig:
 * ► Verwende hierzu NumberDecimal!
 * ► Konvertiere dazu den "items.amount" zuerst in einen
 * NumberDecimal
 * ► Und summiere anschließend die Elemente auf
 * ► Ggf. wirst du hierfür den $map-Operator benötigen, da
 * $toDecimal nur auf einem einzelnen Element
 * angewendet werden kann
 */
db.invoices.aggregate([
    {$addFields: {
            totalAmountDecimal: {
                $sum: {
                    $map: {
                        input: "$items",
                        as: "item",
                        in: {
                            $toDecimal: "$$item.amount"
                        }
                    }
                }
            }
        }}
])

db.invoices.updateMany({}, [
    {$addFields: {
            totalAmountDecimal: {
                $sum: {
                    $map: {
                        input: "$items",
                        as: "item",
                        in: {
                            $toDecimal: "$$item.amount"
                        }
                    }
                }
            }
        }}
])
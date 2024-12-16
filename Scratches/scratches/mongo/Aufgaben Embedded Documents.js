/**
 * Aufgaben aus dem Abschnitt 4 Embedded Documents
 * Einfache find() abfragen in der Collection launches
 */
// um launches zu verwenden muss vohrer "use udemy-mongo" eingegeben werden

/**
 *    Aufgabe 1:
 *     • Wie viele „Falcon 9"-Raketen wurden insgesamt gestartet?
 *    • Tipp: Du findest dies in der Eigenschaft „rocket.name"
 */
db.launches.find({"rocket.name": "Falcon 9"}).count()
//--> Ergebnis 137

/**
 *    Aufgabe 2:
 *    • Gebe den ersten Missionsnamen (Eigenschaft: name) des Raketenstartes aus, der Fracht sowohl für „Hong Kong", als auch für „France" transportiert hat
 *    • Hinweis: Dies kann auch ein Satellit für z.B. „Hong Kong", und ein weiterer, anderer Satellit für „France" gewesen sein
 *    • Sortiere dazu die Daten nach „date_utc" aufsteigend
 *    • Und gebe den Namen der ersten Mission aus!
 *    • Tipp: Das Land der Fracht findet sich unter "payloads.nationalities"
 */
// Folgende Query ist falsch, da sie nur die letzte Bedingung berücksichtigt --> Es muss ein $and verwendet werden
db.launches.find({"payloads.nationalities": "Hong Kong", "payloads.nationalities": "France"}).sort({"date_utc": 1})
// So sieht die richtige schreibweise aus:
db.launches.find(
    {$and:
            [
                {"payloads.nationalities": "Hong Kong"},
                {"payloads.nationalities": "France"}]
    },
    {"payloads.nationalities": true, name: true, date_utc: true}
).sort({"date_utc": 1}).limit(1)
// Ergebnis: name: 'ABS-3A / Eutelsat 115W B',

/**
 *    Aufgabe 3:
 *    • Gebe den Missionsnamen der ersten Rakete aus, die als Nutzlast einen „Satellite" für das Land „Luxembourg" in den Weltraum geschickt hat
 *    • Sortiere dazu die Daten nach „date_utc" aufsteigend und gebe den Namen des ersten Eintrages aus
 *    • Tipp: Den Typ der Nutzlast findet sich unter payloads.type
 */
db.launches.find(
    {"payloads": {$elemMatch: { $and: [{"type": "Satellite"}, {"nationalities": "Luxembourg"}]}}},
    {"payloads.type": true, "payloads.nationalities": true, name: true}
).sort({date_utc: 1}).limit(1)
// Ergenis: SES-8 ist die erste die einen Satelliten für Luxemburg ins All geschickt hat

/**
 *    Aufgabe 4:
 *    • In der Eigenschaft „ships.home_port" findest du die Stadt, in dem ein Schiff von Spacex seinen Heimthafen hat (dort landen Raketen z.B. auf dem Ozean)
 *    • Bennene hier den Hafen „Port of Los Angeles" in „Port of LA" um
 *    • Bitte beachte hierbei: Es kann hierbei mehrere "ships" pro Rakete geben
 *    • Du wirst hier also einen $arrayFilters benötigen
 */
db.launches.updateMany(
    {"ships.home_port": "Port of Los Angeles"}, // Match nach allen Einträgen mit home_port = Port of Los Angeles
    {$set: {"ships.$[ship].home_port": "Port of LA"}}, // Setze den Wert von home_port auf Port of LA
    {arrayFilters: [{"ship.home_port": "Port of Los Angeles"}]} // Filtere nach allen Einträgen mit home_port = Port of Los Angeles
)

/**
 *     Aufgabe 5 (schwer)
 *    • Bei der Nutzlast wird unter "payloads.nationalities" das Land angegeben
 *    • Benenne das Land "United States" in USA um
 *    • Bitte beachte: Von der Datenstruktur her, eine Rakete kann mehrere Payloads haben, und ein Payload kann mehrere Payloads haben, und ein Payload kann theoretisch mehrere Nationalities haben
 *    • Du wirst hier also 2 ArrayFilters benötigen
 */
db.launches.updateMany(
    {"payloads.nationalities": "United States"}, // Match nach allen Einträgen mit nationalities = United States
    {$set: { "payloads$[payload].nationalities.$[nationality]": "USA"}}, // Setze den Wert von nationalities auf USA
    { arrayFilters: [{"payload.nationalities": "United States"}, {"nationality": "United States"}]} // Filtere nach allen Einträgen mit nationalities = United States
)
// 107 Dokuemnte wurden aktualisiert
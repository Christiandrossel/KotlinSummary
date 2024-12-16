// Skript zum Löschen aller Indizes außer dem _id-Index in allen Collections einer Datenbank

// Wähle die Datenbank aus (hier "licences" als Beispiel, anpassen nach Bedarf)
var dbName = "licences";
var db = db.getSiblingDB(dbName);

// Hole alle Collection-Namen der Datenbank
var collections = db.getCollectionNames();

collections.forEach(function(collectionName) {
    print("Dropping indexes for collection: " + collectionName);

    // Hole alle Indizes der Collection
    var indexes = db.getCollection(collectionName).getIndexes();

    indexes.forEach(function(index) {
        // Lösche den Index, wenn er nicht _id ist
        if (index.name !== '_id_') {
            print("Dropping index: " + index.name + " in collection: " + collectionName);
            db.getCollection(collectionName).dropIndex(index.name);
        }
    });
});

print("Finished dropping all non-_id indexes.");




/**
 * Funktioniert nicht
 * @type {string[]}
 */
// Liste der Datenbanken, für die die Indizes gelöscht werden sollen
var dbNames = ["drm", "licences", "links", "messages", "onleihe-management", "ratings", "scheduling", "sync", "ui", "users"];

// Schleife durch jede Datenbank
dbNames.forEach(function(dbName) {
    var db = db.getSiblingDB(dbName); // Wechsel zur aktuellen Datenbank
    print("Processing database: " + dbName);

    // Hole alle Collection-Namen der Datenbank
    var collections = db.getCollectionNames();

    collections.forEach(function(collectionName) {
        print("Dropping indexes for collection: " + collectionName);

        // Hole alle Indizes der Collection
        var indexes = db.getCollection(collectionName).getIndexes();

        indexes.forEach(function(index) {
            // Lösche den Index, wenn er nicht _id ist
            if (index.name !== '_id_') {
                print("Dropping index: " + index.name + " in collection: " + collectionName);
                db.getCollection(collectionName).dropIndex(index.name);
            }
        });
    });

    print("Finished processing database: " + dbName);
});

print("Finished dropping all non-_id indexes for all databases.");



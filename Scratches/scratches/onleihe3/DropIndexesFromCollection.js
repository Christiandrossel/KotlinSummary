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

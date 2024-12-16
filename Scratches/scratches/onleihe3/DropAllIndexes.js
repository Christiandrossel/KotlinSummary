/**
 * Entfernt aus allen Collections alle Indexe
 * Skript zum Löschen aller Indizes außer dem _id-Index in allen Collections einer Datenbank
 */


// Liste der Datenbanken, für die die Indizes gelöscht werden sollen
var dbNames = ["drm", "licences", "links", "messages", "onleihe-management", "ratings", "scheduling", "sync", "ui", "users"];

// Schleife durch jede Datenbank
dbNames.forEach(function(dbName) {

    // Get all collections of the database
    var collections = db.getSiblingDB(dbName).getCollectionNames();

    collections.forEach(function(collectionName) {
        print("Dropping indexes for collection: " + collectionName);

        if (!db.getCollection(collectionName).exists()) {
            print("Collection does not exist: " + collectionName);
            return;
        }

        // Get all indexes of the collection
        var indexes = db.getCollection(collectionName).getIndexes();
        if (indexes.length < 1) {
            print("No indexes to drop for collection: " + collectionName);
            return;
        }

        indexes.forEach(function(index) {
            // Drop all indexes except the _id index
            if (index.name !== '_id_') {
                print("Dropping index: " + index.name + " in collection: " + collectionName);
                try {
                    db.getCollection(collectionName).dropIndex(index.name)
                } catch (e) {
                    print("Error dropping index: " + index.name + " in collection: " + collectionName);
                    print(e);
                }
            }
        });
    });

    print("Finished processing database: " + dbName);
});

print("Finished dropping all non-_id indexes for all databases.");


function removeIndexesFromDatabases() {

// Liste der Datenbanken, für die die Indizes gelöscht werden sollen
    var dbNames = ["drm", "licences", "links", "messages", "onleihe-management", "ratings", "scheduling", "sync", "ui", "users"];

// Schleife durch jede Datenbank
    dbNames.forEach(function(dbName) {
        // get to the database example user licences
        var database = db.getSiblingDB(dbName);


        // Get all collections of the database
        var collections = db.getSiblingDB(dbName).getCollectionNames();

        collections.forEach(function(collectionName) {
            print("Dropping indexes for collection: " + collectionName);

            // Get all indexes of the collection
            var indexes = db.getCollection(collectionName).getIndexes();
            print("Indexes for collection: " + indexes);
        });
    });
}


// skript to set indexes
function setIndexesToLicenceDatabase() {

    var dbName = "licences"

    
}
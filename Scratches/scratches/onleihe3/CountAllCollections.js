// List of all databases
var dbNames = ["drm", "licences", "links", "messages", "onleihe-management", "ratings", "scheduling", "sync", "ui", "users"];

var sum = 0
dbNames.forEach(function(dbName) {
    var collections = db.getSiblingDB(dbName).getCollectionNames(); // get all collections of the database

    // add the number of collections to the sum
    sum += collections.length;
    
    print("Database: " + dbName + " has " + collections.length + " collections.");
});

print("Total number of collections in all databases: " + sum);
print("Finished counting collections for all databases.");

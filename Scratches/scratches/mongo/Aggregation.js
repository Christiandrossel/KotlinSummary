/**
 * Es wird ein Array zurückgegeben, in dem jedes Element ein Dokument ist, das die _id und die Anzahl der Dokumente enthält, die in der Gruppe zusammengefasst wurden.
 * $sum: summiert die Werte in der Gruppe.
 */

db.books.aggregate([{$group: {_id: "$published", count: {$sum: 1}}}])

// Es wird ein Array zurückgegeben, in dem jedes Element ein Dokument ist, das die _id und die Anzahl der Dokumente enthält, die in der Gruppe zusammengefasst wurden.

/**
 * Es wird ein Array zurückgegeben, in dem jedes Element ein Dokument ist, das die _id und den ersten Autor in der Gruppe enthält.
 * $first: gibt den ersten Wert zurück, der in der Gruppe gefunden wird.
 */
db.books.aggregate([{$group: {_id: "$published", author: {$first: "$author.name"}}}])

/**
 * $addToSet: fügt den Wert zu einem Array hinzu, wenn er nicht bereits vorhanden ist.
 * Es wird ein Array zurückgegeben, in dem jedes Element ein Dokument ist, das die _id und die Autoren in der Gruppe enthält.
 */
db.books.aggregate([{$group: {_id: "$published", author: {$addToSet: "$author.name"}}}])

/**
 * $sum: summiert die Werte in der Gruppe.
 */
db.books.aggregate([{$group: {_id: "$published", count: {$sum: 1}}}])


/**
 * $sum: 1 --> zählt die Anzahl der Dokumente in der Gruppe.
 * _id: null --> Gruppierung nach allen Dokumenten und nicht nach einem bestimmten Feld.
 */
db.books.aggregate([{$group: {_id: null, count: {$sum: 1}}}])

/**
 * _id: null --> Gruppierung nach allen Dokumenten und nicht nach einem bestimmten Feld.
 * sumPublished: {$sum: "$published"} --> summiert die Werte vom Feld published in der Gruppe.
 * avgPublished: {$avg: "$published"} --> berechnet den Durchschnitt der Werte vom Feld published in der Gruppe.
 */
db.books.aggregate([{
    $group: {
        _id: null,
        sumPublished: {$sum: "$published"},
        avgPublished: {$avg: "$published"}
    }
}])


/**
 * firstChar: {$substrCP: ["$title", 0, 1]} --> extrahiert den ersten Buchstaben aus dem Feld title.
 * year: "$published" --> extrahiert das Feld published.
 */
db.books.aggregate([{
    $group: {
        _id: {
            firstChar: {$substrCP: ["$title", 0, 1]},
            year: "$published"
        },
        count: {$sum: 1}
    }
}])

/**
 * $unwind: {path: "$tags"} --> teilt das Array tags in einzelne Dokumente auf.
 */
db.books.aggregate([
    {$unwind: {path: "$tags"}},
    {$group: {_id: "$tags", count: {$sum: 1}}}
])

/**
 * $lookup: --> verknüpft die Collection bookCategories mit der Collection books.
 * from: "bookCategories" --> Collection, die verknüpft werden soll.
 * localField: "_id" --> Feld, das in der Collection books verknüpft werden soll.
 * foreignField: "title" --> Feld, das in der Collection bookCategories verknüpft werden soll.
 */
db.books.aggregate([
    {$unwind: {path: "$tags"}},
    {$group: {_id: "$tags", count: {$sum: 1}}},
    {$lookup: {from: "bookCategories", localField: "_id", foreignField: "title", as: "bookCategory"}},
])

/**
 * $sort: sortiert die Dokumente nach dem Feld titleLength aufsteigend, title absteigend und isbn absteigend.
 * $skip: 20 --> überspringt die ersten 20 Dokumente.
 * $limit: 1 --> gibt nur ein Dokument zurück.
 */
db.books.aggregate([
    {
        $project: {

            title: "$title",
            isbn: "$isbn",
            titleLength: {
                $strLenCP: "$title"
            }
        }
    },
    {
        $sort: {
            titleLength: 1,
            title: -1,
            isbn: -1
        }
    },
    {$skip: 20},
    {$limit: 1}
])

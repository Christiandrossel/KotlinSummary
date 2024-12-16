/**
 * Mongo Shell Commands
 */

// get all books
db.books.aggregate([
    {$project: {
        tags: true,
            firstTag: {$arrayElemAt: ["$tags", 0]},
            cTags: {$concatArrays: ["$tags", ["Test"]]},
            isSachbuch: {$in: ["Sachbuch", "$tags"]},
            lTags: {$map:{input: "$tags", as: "tag", in: {$strLenCP: "$$tag"}}},
            numTags: {$size: "$tags"}
        }}
])

// get all books with title and author
db.books.aggregate([
    {$project: {
        title: true,
            fullTitle: {$concat: ["$title", " von ", "$author.name"]},
        }
    }
])

// get all books with with a specific author
db.books.aggregate([
        {$project: {
                "author.name": true,
                tags: true,
                title: true,
                isSpecificAuthor: {
                    $or: [
                        {seq: ["Kayleigh Kovacek", "sauthor.name" ]},
                        {seq: ['Anastacio Rice I', "$author.name" ]}
                    ]
                }
            }
        }
    ])

// map array tags and get the length of each tag
db.books.aggregate([
    {$project: {
            numCharTags: {$map:{input: "$tags", as: "tag", in: {$strLenCP: "$$tag"}}}
        }
    }
])

// get the length of each tag
db.books.aggregate([
    {$project: {
        tags: true,
            tagLength: {$sum: {$map:{input: "$tags", as: "tag", in: {$strLenCP: "$$tag"}}}}
        }
    }
])

// change datat type from published int to string
db.books.aggregate([
    {$project: {
        published: true,
            publishedString: {$toString: "$published"}
        }
    }
])

db.books.aggregate([
    {$project: {
            isbn: true,
            isbnLong: {$convert: {input: "$isbn", to: "long"}}
        }
    }
])

// write all data to books2
db.books.aggregate([
    {$out: "books2"}
])

// update book add field titleLength
db.books.updateMany({}, [
    {$addFields: {titleLength: {$strLenCP: "$title"}}}
])


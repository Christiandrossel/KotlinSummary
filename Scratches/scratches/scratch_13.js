/**
 * Sucht alle Lizenzen eines Users und sortiert diese nach dem Datum der letzten Änderung des Produktes
 * $match: Filtert alle Lizenzen anhand der id des Eigentümers
 * $addFields: Konvertiert die productId in ein ObjectId
 * $lookup: Verknüpft die Lizenz mit dem Produkt. Dabei wird in die Collection product geschaut und die productId mit dem _id des Produktes verglichen
 * $unwind: Entpackt das Array productInfo. PreserveNullAndEmptyArrays: true sorgt dafür, dass auch Lizenzen ohne Produkt ausgegeben werden
 * $sort: Sortiert die Lizenzen nach dem Datum der letzten Änderung des Produktes
 */
db.licence.aggregate(
    [
        {
            $match: {
                ownerId: "65536ca58f58da2a022b505e",
            },
        },
        {
            $addFields: {
                convertedProductId: {
                    $toObjectId: "$productId",
                },
            },
        },
        {
            $lookup: {
                from: "product",
                localField: "convertedProductId",
                foreignField: "_id",
                as: "productInfo",
            },
        },
        {
            $unwind: {
                path: "$productInfo",
                preserveNullAndEmptyArrays: true,
            },
        },
        {
            $match:
            /**
             * query: The query in MQL.
             */
                {
                    "productInfo.updated": {
                        $gt: new Date("2024-01-05"),
                    },
                    "productInfo.mediaType": "E_BOOK",
                },
        },
        {
            $sort: {
                "productInfo.updated": -1, // Use -1 for descending order
            },
        },
        {
            $project:
            /**
             * specifications: The fields to
             *   include or exclude.
             */
                {
                    productId: 1,
                },
        },
    ]
)
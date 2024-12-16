val url = "http://localhost:8080/v1/ratings?userId=active-user&profileId=master&onleiheId=onleihe-1&libraryId=library-1&productRatingCommentSize=10"
val urlUiService = "https://localhost:8080/v1/ratings?userId=active-user&profileId=master&productRatingCommentSize=10&onleiheId=onleihe-1&libraryId=library-1"

// compare url and urlUiService
println(url == urlUiService) // false
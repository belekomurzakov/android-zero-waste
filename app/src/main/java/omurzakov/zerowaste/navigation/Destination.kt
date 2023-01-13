package omurzakov.zerowaste.navigation

sealed class Destination(
    val route: String
) {
    object ListOfCategory : Destination(route = "list_of_category")
    object CategoryDetailScreen : Destination(route = "category_detail")
    object MapScreen : Destination(route = "map")
    object HomeScreen : Destination(route = "home")
    object PhotoScreen : Destination(route = "photo")
    object HistoryDetail : Destination(route = "history")
}
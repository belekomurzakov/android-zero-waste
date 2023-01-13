package omurzakov.zerowaste.navigation

import androidx.navigation.NavController

class NavigationRouterImpl(private val navController: NavController) : INavigationRouter {

    override fun getNavController(): NavController = navController

    override fun returnBack() {
        navController.popBackStack()
    }

    override fun navigateToListOfCategory() {
        navigate(Destination.ListOfCategory.route)
    }

    override fun navigateToHome() {
        navigate(Destination.HomeScreen.route)
    }

    override fun navigateToHistory(id:Long) {
        navController.navigate(Destination.HistoryDetail.route + "/${id}")
    }

    override fun navigateToCategoryDetail(category: String) {
        navController.navigate(Destination.CategoryDetailScreen.route + "/${category}")
    }

    override fun navigateToPhoto() {
        navigate(Destination.PhotoScreen.route)
    }

    override fun navigateToMap(category: String?) {
        navController.navigate(Destination.MapScreen.route + "/${category}")
    }

    private fun navigate(destination: String) {
        navController.navigate(destination) {
            navController.graph.startDestinationRoute?.let { screen_route ->
                popUpTo(screen_route) {
                    saveState = true
                }
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}
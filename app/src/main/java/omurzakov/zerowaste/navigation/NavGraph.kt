package omurzakov.zerowaste.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import omurzakov.zerowaste.ui.screens.camera.GalleryScreen
import omurzakov.zerowaste.ui.screens.category.CategoryScreen
import omurzakov.zerowaste.ui.screens.detail.DetailScreen
import omurzakov.zerowaste.ui.screens.historydetail.HistoryDetailScreen
import omurzakov.zerowaste.ui.screens.home.HomeScreen
import omurzakov.zerowaste.ui.screens.map.MapScreen
import omurzakov.zerowaste.utils.Constant

@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalFoundationApi
@Composable
fun NavGraph(
    navController: NavHostController,
    navigation: INavigationRouter = remember { NavigationRouterImpl(navController) },
    startDestination: String
) {
    NavHost(
        navController = navController, startDestination = startDestination
    ) {
        composable(Destination.ListOfCategory.route) {
            CategoryScreen(navigation = navigation)
        }

        composable(Destination.HistoryDetail.route + "/{id}",
            arguments = listOf(navArgument(Constant.id) {
                type = NavType.LongType
                defaultValue = -1
            })
        ) {
            val id = it.arguments?.getLong(Constant.id)
            HistoryDetailScreen(navigation = navigation, id = id!!)
        }

        composable(Destination.PhotoScreen.route) {
            GalleryScreen(navigation = navigation)
        }

        composable(Destination.HomeScreen.route) {
            HomeScreen(navigation = navigation)
        }

        composable(
            Destination.MapScreen.route + "/{category}",
            arguments = listOf(navArgument(Constant.category) {
                type = NavType.StringType
                defaultValue = "no-category"
            })
        ) {
            val name = it.arguments?.getString(Constant.category)
            MapScreen(navigation = navigation, category = name!!)
        }

        composable(
            Destination.CategoryDetailScreen.route + "/{category}",
            arguments = listOf(navArgument(Constant.category) {
                type = NavType.StringType
                defaultValue = "no-category"
            })
        ) {
            val name = it.arguments?.getString(Constant.category)
            DetailScreen(navigation = navigation, category = name!!)
        }
    }
}
package omurzakov.zerowaste.navigation

import androidx.navigation.NavController

interface INavigationRouter {
    fun getNavController(): NavController
    fun returnBack()
    fun navigateToListOfCategory()
    fun navigateToHome()
    fun navigateToHistory(id:Long)
    fun navigateToPhoto()
    fun navigateToCategoryDetail(category: String)
    fun navigateToMap(category: String?)
}
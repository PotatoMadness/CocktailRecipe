package com.potatomadness.cocktail.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.potatomadness.favorites.navigation.FavoriteRoute
import com.potatomadness.home.navigation.HomeRoute
import com.potatomadness.myrecipe.navigation.MyRecipeRoute

sealed class TopLevelDestination(
    val route: String,
    val icon: ImageVector,
    val position: Int
) {
    object Home: TopLevelDestination(HomeRoute.route, Icons.Filled.Home, 0)
    object Favorite: TopLevelDestination(FavoriteRoute.route, Icons.Filled.Favorite, 1)
    object MyRecipe: TopLevelDestination(MyRecipeRoute.route, Icons.Filled.Add, 2)
}

class CocktailAppNavigationActions(private val navController: NavHostController) {
    fun navigateTo(destination: TopLevelDestination) {
        navController.navigate(destination.route) {
            popUpTo(navController.graph.findStartDestination().id) {
//                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}
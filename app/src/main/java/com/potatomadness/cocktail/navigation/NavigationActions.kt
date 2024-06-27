package com.potatomadness.cocktail.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.potatomadness.favorites.navigation.FAVORITES_ROUTE
import com.potatomadness.home.navigation.HOME_ROUTE
import com.potatomadness.myrecipe.navigation.MY_RECIPE_ROUTE

sealed class TopLevelDestination(
    val route: String,
    val title: String,
    val icon: ImageVector,
    val position: Int
) {
    object Home: TopLevelDestination(HOME_ROUTE, "Home", Icons.Filled.Home, 0)
    object Favorite: TopLevelDestination(FAVORITES_ROUTE, "Favorite", Icons.Filled.Favorite, 1)
    object MyRecipe: TopLevelDestination(MY_RECIPE_ROUTE, "My Recipe", Icons.Filled.Add, 2)
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
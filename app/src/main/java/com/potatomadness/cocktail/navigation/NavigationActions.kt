package com.potatomadness.cocktail.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

object CocktailAppRoute {
    const val HOME = "Home"
    const val FAVORITE = "Favorite"
    const val MY_RECIPE = "MyRecipe"
    const val DETAIL = "Detail"
    const val INFO = "info"
}

sealed class TopLevelDestination(
    val route: String,
    val icon: ImageVector,
    val position: Int
) {
    object Home: TopLevelDestination(CocktailAppRoute.HOME, Icons.Filled.Home, 0)
    object Favorite: TopLevelDestination(CocktailAppRoute.FAVORITE, Icons.Filled.Favorite, 1)
    object MyRecipe: TopLevelDestination(CocktailAppRoute.MY_RECIPE, Icons.Filled.Add, 2)
}

class CocktailAppNavigationActions(private val navController: NavHostController) {
    fun navigateTo(destination: TopLevelDestination) {
        navController.navigate(destination.route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}
package com.potatomadness.favorites.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.potatomadness.favorites.FavoriteRecipeScreen


fun NavController.navigateToFavorites() {
    navigate(FavoriteRoute.route)
}

fun NavGraphBuilder.favoritesScreen(
    onItemClick: (Int) -> Unit
) {
    composable(FavoriteRoute.route) {
        FavoriteRecipeScreen { cocktailId ->
            onItemClick(cocktailId)
        }
    }
}
object FavoriteRoute {
    const val route = "favorites_route"
}
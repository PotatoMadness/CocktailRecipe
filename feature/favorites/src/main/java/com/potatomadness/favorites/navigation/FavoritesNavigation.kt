package com.potatomadness.favorites.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.potatomadness.favorites.FavoriteRecipeScreen

const val FAVORITES_ROUTE = "favorites_route"
fun NavController.navigateToFavorites() {
    navigate(FAVORITES_ROUTE)
}

fun NavGraphBuilder.favoritesScreen(
    isExpanded: Boolean,
    onItemClick: (Int) -> Unit
) {
    composable(FAVORITES_ROUTE) {
        FavoriteRecipeScreen(isExpanded = isExpanded) { cocktailId ->
            onItemClick(cocktailId)
        }
    }
}
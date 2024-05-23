package com.potatomadness.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.potatomadness.home.HomeScreen

fun NavController.navigateToHome() {
    navigate(HomeRoute.route)
}

fun NavGraphBuilder.homeScreen(
    isExpanded: Boolean,
    onItemClick: (Int) -> Unit
) {
    composable(HomeRoute.route) {
        HomeScreen(isExpanded = isExpanded, onDrinkClick = onItemClick)
    }
}
object HomeRoute {
    const val route = "home_route"
}
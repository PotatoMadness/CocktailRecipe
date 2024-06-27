package com.potatomadness.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.potatomadness.home.HomeScreen

const val HOME_ROUTE = "home_route"
fun NavController.navigateToHome() {
    navigate(HOME_ROUTE)
}

fun NavGraphBuilder.homeScreen(
    isExpanded: Boolean,
    onItemClick: (Int) -> Unit
) {
    composable(HOME_ROUTE) {
        HomeScreen(isExpanded = isExpanded, onDrinkClick = onItemClick)
    }
}
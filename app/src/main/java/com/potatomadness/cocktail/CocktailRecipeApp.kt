package com.potatomadness.cocktail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.potatomadness.cocktail.navigation.TopLevelDestination
import com.potatomadness.cocktail.navigation.CocktailAppNavigationActions
import com.potatomadness.cocktail.navigation.CocktailBottomNaviBar
import com.potatomadness.cocktail.navigation.CocktailNavRail

@Composable
fun CocktailRecipeApp(
    windowSize: WindowSizeClass
) {
    val navHostController = rememberNavController()
    val navActions = remember(navHostController) {
        CocktailAppNavigationActions(navController = navHostController)
    }
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val selectedRoute = navBackStackEntry?.destination?.route ?: TopLevelDestination.Home.route

    val isExpandedScreen = windowSize.widthSizeClass == WindowWidthSizeClass.Expanded
    // drawer / bottom tab
    Row(modifier = Modifier.fillMaxSize()) {
        AnimatedVisibility(visible = isExpandedScreen) {
            CocktailNavRail(currentRoute = selectedRoute, navActions = navActions)
        }
        Column(modifier = Modifier.fillMaxSize()) {
            CocktailNavHost(navHostController, isExpandedScreen, Modifier.weight(1f))
            AnimatedVisibility(visible = !isExpandedScreen) {
                CocktailBottomNaviBar(currentRoute = selectedRoute, navActions = navActions)
            }
        }
    }
}
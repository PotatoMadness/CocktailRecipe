package com.potatomadness.cocktail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.potatomadness.cocktail.navigation.TopLevelDestination
import com.potatomadness.cocktail.navigation.CocktailAppNavigationActions
import com.potatomadness.cocktail.navigation.CocktailBottomNaviBar
import com.potatomadness.cocktail.navigation.CocktailNavRail
import kotlinx.coroutines.delay

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
    val isShowingLandingScreen = remember { MutableTransitionState(true) }
    val transition = updateTransition(isShowingLandingScreen, label = "splashTransition")
    val splashAlpha by transition.animateFloat(
        transitionSpec = { tween(durationMillis = 100) }, label = "splashAlpha"
    ) {
        if (it) 1f else 0f
    }
    val contentAlpha by transition.animateFloat(
        transitionSpec = { tween(durationMillis = 300) }, label = "contentAlpha"
    ) {
        if (it) 0f else 1f
    }
    // drawer / bottom tab
    Box(modifier = Modifier.fillMaxSize()) {
        LandingScreen(Modifier.alpha(splashAlpha)) {
            isShowingLandingScreen.targetState = false
        }
        MainContent(
            modifier = Modifier.alpha(contentAlpha),
            isExpandedScreen = isExpandedScreen,
            selectedRoute = selectedRoute,
            navActions = navActions,
            navHostController = navHostController
            )
    }
}

@Composable
fun MainContent(
    modifier: Modifier = Modifier,
    isExpandedScreen: Boolean,
    selectedRoute: String,
    navActions: CocktailAppNavigationActions,
    navHostController: NavHostController
){
    Row(modifier) {
        AnimatedVisibility(visible = isExpandedScreen) {
            CocktailNavRail(currentRoute = selectedRoute, navActions = navActions)
        }
        Column(modifier = modifier.fillMaxSize()) {
            CocktailNavHost(navHostController, isExpandedScreen, modifier.weight(1f))
            AnimatedVisibility(visible = !isExpandedScreen) {
                CocktailBottomNaviBar(currentRoute = selectedRoute, navActions = navActions)
            }
        }
    }
}
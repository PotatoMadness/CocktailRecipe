package com.potatomadness.cocktail

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable

@Composable
fun CocktailRecipeApp(
    windowSize: WindowSizeClass
) {
    val isExpandedScreen = windowSize.widthSizeClass == WindowWidthSizeClass.Expanded
    CocktailNavHost(isExpandedScreen)
}
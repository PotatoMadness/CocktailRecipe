package com.potatomadness.cocktail

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.potatomadness.cocktail.ui.HomeScreen

@Composable
fun CocktailNavHost() {
    val navHostController = rememberNavController()
    NavHost(navController = navHostController, startDestination = "home") {
        composable("home") {
            HomeScreen()
        }
    }
}
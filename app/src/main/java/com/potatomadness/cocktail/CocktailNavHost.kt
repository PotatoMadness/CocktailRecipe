package com.potatomadness.cocktail

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.potatomadness.cocktail.ui.DrinkDetailScreen
import com.potatomadness.cocktail.ui.HomeScreen
import com.potatomadness.cocktail.ui.IngredientScreen

@Composable
fun CocktailNavHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(onDrinkClick =  { drink ->
                navController.navigate("detail/${drink.id}")
            })
        }
        composable("detail/{drinkId}",
            arguments = listOf(
                navArgument("drinkId") { type = NavType.StringType }
            )
        ) {
            DrinkDetailScreen(
                onIngredientClick = { navController.navigate("")}
            )
        }
        composable("info/{ingredientId}",
            arguments = listOf(
                navArgument("ingredientId") { type = NavType.StringType }
            )
        ) {
            IngredientScreen()
        }
    }
}
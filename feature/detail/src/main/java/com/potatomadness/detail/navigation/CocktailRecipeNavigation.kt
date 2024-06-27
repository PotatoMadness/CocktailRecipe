package com.potatomadness.detail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.potatomadness.detail.CocktailRecipeScreen

const val RECIPE_ROUTE = "recipe_route"
internal const val COCKTAIL_ID = "cocktailId"
fun NavController.navigateToCocktailRecipe(cocktailId: Int) {
    navigate("${RECIPE_ROUTE}/${cocktailId}")
}

fun NavGraphBuilder.cocktailRecipeScreen(
    onRecipeStepClick: (String) -> Unit,
    onFabClick: (Int) -> Unit,
    onBackPressed: () -> Unit
) {
    composable(route = "${RECIPE_ROUTE}/{${COCKTAIL_ID}}",
        arguments = listOf(
            navArgument(COCKTAIL_ID) {
                defaultValue = -1
                nullable = false
                type = NavType.IntType
            }
        )
    ) {
        CocktailRecipeScreen (onRecipeStepClick = onRecipeStepClick, onFabClick = onFabClick, onBackPressed = onBackPressed)
    }
}
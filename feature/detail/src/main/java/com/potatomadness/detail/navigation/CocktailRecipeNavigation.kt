package com.potatomadness.detail.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.potatomadness.detail.CocktailRecipeScreen

fun NavController.navigateToCocktailRecipe(cocktailId: Int) {
    navigate(RecipeRoute.detailRoute(cocktailId.toString()))
}

@RequiresApi(Build.VERSION_CODES.HONEYCOMB_MR2)
fun NavGraphBuilder.cocktailRecipeScreen(
    onRecipeStepClick: (String) -> Unit,
    onFabClick: (Int) -> Unit,
    onBackPressed: () -> Unit
) {
    composable(route = RecipeRoute.detailRoute("{${RecipeRoute.cocktailId}}"),
        arguments = listOf(
            navArgument(RecipeRoute.cocktailId) {
                defaultValue = -1
                nullable = false
                type = NavType.IntType
            }
        )
    ) {
        CocktailRecipeScreen (onRecipeStepClick = onRecipeStepClick, onFabClick = onFabClick, onBackPressed = onBackPressed)
    }
}
object RecipeRoute {
    const val route = "recipe_route"
    const val cocktailId = "cocktailId"
    fun detailRoute(id: String): String = "$route/$id"
}
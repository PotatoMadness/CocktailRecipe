package com.potatomadness.detail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.potatomadness.detail.IngredientInfoScreen

fun NavController.navigateToIngredientInfo(name: String) {
    navigate(IngredientRoute.detailRoute(name))
}

fun NavGraphBuilder.ingredientInfoScreen(
    onBackPressed: () -> Unit
) {
    composable(route = IngredientRoute.detailRoute("{${IngredientRoute.ingredientName}}"),
        arguments = listOf(
            navArgument(IngredientRoute.ingredientName) {
                defaultValue = -1
                nullable = false
                type = NavType.StringType
            }
        )
    ) {
        IngredientInfoScreen (onBackPressed = onBackPressed)
    }
}
object IngredientRoute {
    const val route = "ingredient_route"
    const val ingredientName = "ingredientName"
    fun detailRoute(name: String): String = "$route/$name"
}
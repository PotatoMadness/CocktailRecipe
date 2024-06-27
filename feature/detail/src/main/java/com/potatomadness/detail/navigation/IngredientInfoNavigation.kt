package com.potatomadness.detail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.potatomadness.detail.IngredientInfoScreen

const val INGREDIENT_ROUTE = "ingredient_route"
internal const val INGREDIENT_NAME = "ingredient_name"
fun NavController.navigateToIngredientInfo(name: String) {
    navigate("${INGREDIENT_ROUTE}/$name")
}

fun NavGraphBuilder.ingredientInfoScreen(
    onBackPressed: () -> Unit
) {
    composable(route = "$INGREDIENT_ROUTE/{${INGREDIENT_NAME}}",
        arguments = listOf(
            navArgument(INGREDIENT_NAME) {
                defaultValue = null
                nullable = true
                type = NavType.StringType
            }
        )
    ) {
        IngredientInfoScreen (onBackPressed = onBackPressed)
    }
}
package com.potatomadness.myrecipe.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.potatomadness.myrecipe.MyRecipeCreateScreen
import com.potatomadness.myrecipe.MyRecipeScreen


const val MY_RECIPE_ROUTE = "my_recipe_route"
internal const val COCKTAIL_ID = "cocktailId"
internal class RecipeArgs(val id: Int) {
    constructor(savedStateHandle: SavedStateHandle) :
        this(savedStateHandle.get<Int>(COCKTAIL_ID) ?: -1)
}

fun NavController.navigateToMyRecipe() {
    navigate(MY_RECIPE_ROUTE)
}

fun NavController.navigateToCreateRecipe(cocktailId: Int) {
    navigate("$MY_RECIPE_ROUTE/$cocktailId")
}
fun NavGraphBuilder.myRecipeScreen(
    onCreateClick: () -> Unit,
    onItemClick: (Int) -> Unit
) {
    composable(MY_RECIPE_ROUTE) {
        MyRecipeScreen(onCreateClick, onItemClick)
    }
}


fun NavGraphBuilder.createRecipeScreen(
    onBackPressed: () -> Unit,
    onCreateDone: () -> Unit
) {
    composable(route = "$MY_RECIPE_ROUTE/{${COCKTAIL_ID}}",
        arguments = listOf(
            navArgument(COCKTAIL_ID) {
                defaultValue = -1
                nullable = false
                type = NavType.IntType
            }
        )
    ) {
        MyRecipeCreateScreen(onBackPressed = onBackPressed, onCreateDone = onCreateDone)
    }
}

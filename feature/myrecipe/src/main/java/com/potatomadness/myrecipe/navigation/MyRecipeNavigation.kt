package com.potatomadness.myrecipe.navigation

import com.potatomadness.myrecipe.MyRecipeScreen
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.potatomadness.myrecipe.MyRecipeCreateScreen

fun NavController.navigateToMyRecipe() {
    navigate(MyRecipeRoute.route)
}

fun NavController.navigateToCreateRecipe(cocktailId: Int) {
    navigate(MyRecipeRoute.createRoute(cocktailId.toString()))
}
fun NavGraphBuilder.myRecipeScreen(
    onCreateClick: () -> Unit,
    onItemClick: (Int) -> Unit
) {
    composable(MyRecipeRoute.route) {
        MyRecipeScreen(onCreateClick, onItemClick)
    }
}


fun NavGraphBuilder.createRecipeScreen(
    onBackPressed: () -> Unit
) {
    composable(route = MyRecipeRoute.createRoute("{${MyRecipeRoute.cocktailId}}"),
        arguments = listOf(
            navArgument(MyRecipeRoute.cocktailId) {
                defaultValue = -1
                nullable = false
                type = NavType.IntType
            }
        )
    ) {
        MyRecipeCreateScreen(onBackPressed = onBackPressed)
    }
}
object MyRecipeRoute {
    const val route = "my_recipe_route"
    const val cocktailId = "cocktailId"
    fun createRoute(id: String): String = "$route/$id"
}

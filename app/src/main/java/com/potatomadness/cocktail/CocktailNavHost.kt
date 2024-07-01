package com.potatomadness.cocktail

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.potatomadness.detail.navigation.cocktailRecipeScreen
import com.potatomadness.detail.navigation.ingredientInfoScreen
import com.potatomadness.detail.navigation.navigateToCocktailRecipe
import com.potatomadness.detail.navigation.navigateToIngredientInfo
import com.potatomadness.favorites.navigation.favoritesScreen
import com.potatomadness.home.navigation.HOME_ROUTE
import com.potatomadness.home.navigation.homeScreen
import com.potatomadness.myrecipe.navigation.createRecipeScreen
import com.potatomadness.myrecipe.navigation.myRecipeScreen
import com.potatomadness.myrecipe.navigation.navigateToCreateRecipe
import com.potatomadness.myrecipe.navigation.navigateToMyRecipe

@Composable
fun CocktailNavHost(
    navController: NavHostController,
    isExpanded: Boolean,
    modifier: Modifier = Modifier
) {
    NavHost(navController = navController,
        startDestination = HOME_ROUTE,
        modifier = modifier) {

        homeScreen(isExpanded = isExpanded,
            onItemClick =  { cocktailId -> navController.navigateToCocktailRecipe(cocktailId) }
        )
        cocktailRecipeScreen(
            onRecipeStepClick = { ingredient -> navController.navigateToIngredientInfo(ingredient) },
            // TODO :: 칵테일 등록 성공 시 화면 나가지도록
            onFabClick = { cocktailId -> navController.navigateToCreateRecipe(cocktailId) },
            onBackPressed = { navController.navigateUp() }
        )
        ingredientInfoScreen(
            onBackPressed = { navController.navigateUp() }
        )

        favoritesScreen(isExpanded = isExpanded,
            onItemClick = { id -> navController.navigateToCocktailRecipe(id) }
        )

        myRecipeScreen(
            onCreateClick = { navController.navigateToCreateRecipe(-1) },
            onItemClick = { cocktailId -> navController.navigateToCocktailRecipe(cocktailId) }
        )

        createRecipeScreen(
            onBackPressed = { navController.navigateUp() },
            onCreateDone = {
                navController.navigateUp()
                navController.navigateToMyRecipe()
            }
        )
    }
}
package com.potatomadness.cocktail

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.potatomadness.cocktail.navigation.CocktailAppRoute
import com.potatomadness.common.Const
import com.potatomadness.detail.CocktailDetailScreen
import com.potatomadness.detail.IngredientInfoScreen
import com.potatomadness.favorite.FavoriteRecipeScreen
import com.potatomadness.home.HomeScreen
import com.potatomadness.myrecipe.MyRecipeCreateScreen
import com.potatomadness.myrecipe.MyRecipeScreen

@Composable
fun CocktailNavHost(
    navController: NavHostController,
    isExpanded: Boolean,
    modifier: Modifier = Modifier
) {
    NavHost(navController = navController,
        startDestination = CocktailAppRoute.HOME,
        modifier = modifier) {
        composable(CocktailAppRoute.HOME) {
            HomeScreen(
                isExpanded = isExpanded,
                onDrinkClick =  { cocktailId -> navController.navigate("${CocktailAppRoute.DETAIL}/$cocktailId")
            })
        }
        composable("${CocktailAppRoute.DETAIL}/{${Const.COCKTAIL_ID_SAVED_STATE_KEY}}",
            arguments = listOf(
                navArgument(Const.COCKTAIL_ID_SAVED_STATE_KEY) { type = NavType.IntType }
            )
        ) {
            CocktailDetailScreen(
                onRecipeStepClick = { step -> navController.navigate("info/${step.ingName}") },
                onFabClick = { cocktailId -> navController.navigate("${CocktailAppRoute.CREATE_RECIPE}/$cocktailId") },
                onBackPressed = { navController.navigateUp() }
            )
        }
        composable("${CocktailAppRoute.INFO}/{${Const.INGREDIENT_NAME_SAVED_STATE_KEY}}",
            arguments = listOf(
                navArgument(Const.INGREDIENT_NAME_SAVED_STATE_KEY) { type = NavType.StringType }
            )
        ) {
            IngredientInfoScreen(
                onBackPressed = { navController.navigateUp() },
            )
        }
        composable(CocktailAppRoute.FAVORITE) {
            FavoriteRecipeScreen { cocktailId ->
                navController.navigate("${CocktailAppRoute.DETAIL}/$cocktailId")
            }
        }
        composable(CocktailAppRoute.MY_RECIPE) {
            MyRecipeScreen(
                onClickCreate = { navController.navigate("${CocktailAppRoute.CREATE_RECIPE}/-1") },
                onRecipeClick = { cocktailId -> navController.navigate("${CocktailAppRoute.DETAIL}/$cocktailId") }
            )
        }
        composable("${CocktailAppRoute.CREATE_RECIPE}/{${Const.COCKTAIL_ID_SAVED_STATE_KEY}}",
            arguments = listOf(
                navArgument(Const.COCKTAIL_ID_SAVED_STATE_KEY) { type = NavType.IntType }
            )
        ) {
            MyRecipeCreateScreen(
                onBackPressed = { navController.navigateUp() }
            )
        }
    }
}
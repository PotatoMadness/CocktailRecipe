package com.potatomadness.cocktail

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.potatomadness.cocktail.navigation.CocktailAppRoute
import com.potatomadness.cocktail.ui.CocktailDetailScreen
import com.potatomadness.cocktail.ui.FavoriteRecipeScreen
import com.potatomadness.cocktail.ui.HomeScreen
import com.potatomadness.cocktail.ui.IngredientInfoScreen
import com.potatomadness.cocktail.ui.MyRecipeCreateScreen

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
                navArgument(Const.COCKTAIL_ID_SAVED_STATE_KEY) { type = NavType.StringType }
            )
        ) {
            CocktailDetailScreen(
                onIngredientClick = { ingredientName -> navController.navigate("info/$ingredientName")},
                onFabClick = { cocktailId -> navController.navigate("${CocktailAppRoute.MY_RECIPE}/$cocktailId")},
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
        composable("${CocktailAppRoute.MY_RECIPE}/{${Const.COCKTAIL_ID_SAVED_STATE_KEY}}",
            arguments = listOf(
                navArgument(Const.COCKTAIL_ID_SAVED_STATE_KEY) { type = NavType.StringType }
            )
        ) {
            MyRecipeCreateScreen(
                onBackPressed = { navController.navigateUp() }
            )
        }
    }
}
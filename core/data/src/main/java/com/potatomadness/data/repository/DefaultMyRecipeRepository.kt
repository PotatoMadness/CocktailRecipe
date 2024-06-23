package com.potatomadness.data.repository

import com.potatomadness.data.dao.CocktailDao
import com.potatomadness.data.model.Cocktail
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class DefaultMyRecipeRepository @Inject constructor(
    private val cocktailDao: CocktailDao
): MyRecipeRepository {
    override fun getAllMyRecipe(): Flow<List<Cocktail>> = cocktailDao.getMyRecipes()

    override suspend fun addNewRecipe(cocktail: Cocktail) {
        cocktailDao.insertFullInfo(cocktail = cocktail)
    }
}
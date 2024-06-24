package com.potatomadness.data.repository

import com.potatomadness.database.dao.CocktailDao
import com.potatomadness.database.model.CocktailEntity
import com.potatomadness.database.model.asExternalModel
import com.potatomadness.model.Cocktail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class DefaultMyRecipeRepository @Inject constructor(
    private val cocktailDao: CocktailDao
): MyRecipeRepository {
    override fun getAllMyRecipe(): Flow<List<Cocktail>> = cocktailDao.getMyRecipes().map {
        it.map { entity -> entity.asExternalModel() }
    }

    override suspend fun addNewRecipe(cocktail: Cocktail) {
        cocktailDao.insertFullInfo(CocktailEntity(cocktail))
    }
}
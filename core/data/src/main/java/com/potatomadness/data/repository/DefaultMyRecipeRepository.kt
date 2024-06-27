package com.potatomadness.data.repository

import com.potatomadness.database.dao.CocktailDao
import com.potatomadness.database.model.CocktailEntity
import com.potatomadness.database.model.asExternalModel
import com.potatomadness.database.util.Const.MAX_RECIPE_ID
import com.potatomadness.model.Cocktail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.math.max

internal class DefaultMyRecipeRepository @Inject constructor(
    private val cocktailDao: CocktailDao
): MyRecipeRepository {
    override fun getAllMyRecipe(): Flow<List<Cocktail>> = cocktailDao.getMyRecipes().map {
        it.map { entity -> entity.asExternalModel() }
    }

    override suspend fun addNewRecipe(cocktail: Cocktail) {
        val lastId = max(cocktailDao.getLastId(), MAX_RECIPE_ID) + 1
        cocktailDao.insertFullInfo(CocktailEntity(cocktail.copy(id = lastId, isCustom = true)))
    }
}
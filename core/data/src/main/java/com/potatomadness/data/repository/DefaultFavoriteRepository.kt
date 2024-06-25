package com.potatomadness.data.repository

import com.potatomadness.database.dao.CocktailDao
import com.potatomadness.database.model.CocktailEntity
import com.potatomadness.database.model.asExternalModel
import com.potatomadness.model.Cocktail
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultFavoriteRepository @Inject constructor(
    private val cocktailDao: CocktailDao
): FavoriteRepository {
    override fun isFavoriteCocktail(id: Int) = cocktailDao.isFavorite(id)

    override fun getFavoriteCocktails() = cocktailDao.getFavoriteRecipes().map {
        it.map { entity ->
            entity.asExternalModel()
        }
    }

    override suspend fun toggleFavorite(isFavorite: Boolean, cocktailId: Int)
        = cocktailDao.updateFavorite(to = !isFavorite, id = cocktailId)
}
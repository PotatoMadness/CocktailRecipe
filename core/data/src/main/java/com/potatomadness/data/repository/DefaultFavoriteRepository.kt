package com.potatomadness.data.repository

import com.potatomadness.data.dao.CocktailDao
import com.potatomadness.data.model.Cocktail
import javax.inject.Inject

class DefaultFavoriteRepository @Inject constructor(
    private val cocktailDao: CocktailDao
): FavoriteRepository {
    override fun isFavoriteCocktail(id: Int) = cocktailDao.isFavorite(id)

    override fun getFavoriteCocktails() = cocktailDao.getFavoriteRecipes()

    override suspend fun toggleFavorite(isFavorite: Boolean, cocktail: Cocktail)
        = cocktailDao.update(cocktail.copy(isFavorite = !isFavorite))
}
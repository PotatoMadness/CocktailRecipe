package com.potatomadness.data.repository

import com.potatomadness.model.Cocktail
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun isFavoriteCocktail(id: Int): Flow<Boolean>

    fun getFavoriteCocktails(): Flow<List<Cocktail>>

    suspend fun toggleFavorite(isFavorite: Boolean, id: Int)
}
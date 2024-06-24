package com.potatomadness.data.repository

import com.potatomadness.model.Cocktail
import kotlinx.coroutines.flow.Flow

interface MyRecipeRepository {

    fun getAllMyRecipe(): Flow<List<Cocktail>>

    suspend fun addNewRecipe(cocktail: Cocktail)
}
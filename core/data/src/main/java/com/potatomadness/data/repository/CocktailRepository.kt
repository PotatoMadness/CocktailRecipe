package com.potatomadness.data.repository

import com.potatomadness.model.Cocktail
import com.potatomadness.data.model.FilterType
import com.potatomadness.model.Ingredient
import com.potatomadness.data.model.SearchQuery
import kotlinx.coroutines.flow.Flow

interface CocktailRepository {
    suspend fun getFilterList(type: FilterType): List<String>
    suspend fun getDrinks(query: SearchQuery): List<Cocktail>
    fun getCocktailRecipe(id: Int): Flow<Cocktail>
    suspend fun getIngredientInfo(name: String): Ingredient
    fun getIngredients(): Flow<List<Ingredient>>
    suspend fun isExist(name: String): Boolean
}
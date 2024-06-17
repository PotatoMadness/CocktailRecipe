package com.potatomadness.data.repository

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.potatomadness.data.api.CocktailService
import com.potatomadness.data.dao.CocktailDao
import com.potatomadness.data.dao.IngredientDao
import com.potatomadness.data.database.FirebaseDatabase
import com.potatomadness.data.model.Cocktail
import com.potatomadness.data.model.CocktailResponse
import com.potatomadness.data.model.FilterType
import com.potatomadness.data.model.Ingredient
import com.potatomadness.data.model.SearchQuery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class CocktailRepository @Inject constructor(
    private val cocktailService: CocktailService,
    private val cocktailDao: CocktailDao,
    private val ingredientDao: IngredientDao,
    private val firebaseDatabase: FirebaseDatabase
){
    suspend fun getFilterList(
        type: FilterType
        ): List<String> {

        val filterMap = mutableMapOf<String, String>()
        if (type is FilterType.Alcoholic) filterMap["a"] = "list"
        if (type is FilterType.Category) filterMap["c"] = "list"
        if (type is FilterType.Ingredient) filterMap["i"] = "list"
        if (type is FilterType.Glass) filterMap["g"] = "list"

        val response = cocktailService.getFilterList(filterMap)
        val categoryList = mutableListOf<String>()
        return response.categoryList.mapTo(categoryList) { it.filterName }
    }

    suspend fun getDrinks(query: SearchQuery): List<Cocktail> {
        val result = if (query.searchOrFilter) {
            cocktailService.searchDrinksByAlpha(query.query).cocktailList
        } else {
            val filter = query.filterType ?: throw Error()
            val filterMap = mutableMapOf<String, String>()
            filterMap[filter.tag] = query.query
            cocktailService.getFilteredDrinks(filterMap).cocktailList
        }.map { it.toCocktail() }

        // collect brief information
//        result.forEach {
//            cocktailDao.insert(it)
//        }
        return result
    }

    suspend fun getDrinkRecipe(id: Int): Cocktail {
        if (cocktailDao.isExist(id)) return cocktailDao.getRecipeById(id)
        val result = cocktailService.getDrinkRecipe(id).cocktailList.first().toCocktail()
        result.recipeSteps.forEach {
            ingredientDao.insert(Ingredient(name = it.ingName))
        }
        cocktailDao.upsert(cocktail = result)
        return result
    }

    suspend fun getIngredientInfo(name: String): Ingredient {
        val result = cocktailService.searchIngredientInfo(name).ingredientList.first()
        ingredientDao.insertFullInfo(result)
        return result
    }

    fun getFavoriteCocktails() = cocktailDao.getFavoriteRecipes()

    fun getMyRecipes() = cocktailDao.getMyRecipes()

    fun getIngredients() = ingredientDao.getAll()

    fun isFavoriteCocktail(id: Int) = cocktailDao.isFavorite(id)

    suspend fun isExist(name: String) = cocktailDao.isExist(name)

    suspend fun createNewRecipe(cocktail: Cocktail) {
        cocktailDao.insertFullInfo(cocktail = cocktail)
    }

    suspend fun toggleFavorite(isFavorite: Boolean, cocktail: Cocktail) {
        cocktailDao.update(cocktail.copy(isFavorite = !isFavorite))
    }

    val popularRecipeFlow = firebaseDatabase.popularRecipeFlow
}
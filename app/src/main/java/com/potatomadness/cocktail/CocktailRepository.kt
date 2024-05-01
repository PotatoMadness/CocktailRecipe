package com.potatomadness.cocktail

import com.potatomadness.cocktail.data.Cocktail
import com.potatomadness.cocktail.data.CocktailDao
import com.potatomadness.cocktail.data.FilterType
import com.potatomadness.cocktail.data.Ingredient
import com.potatomadness.cocktail.data.SearchQuery
import javax.inject.Inject

class CocktailRepository @Inject constructor(
    private val cocktailService: CocktailService,
    private val cocktailDao: CocktailDao
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
        return if (query.searchOrFilter) {
            cocktailService.searchDrinksByAlpha(query.query).cocktailList
        } else {
            val filter = query.filterType ?: throw Error()
            val filterMap = mutableMapOf<String, String>()
            filterMap[filter.tag] = query.query
            cocktailService.getFilteredDrinks(filterMap).cocktailList
        }
    }

    suspend fun getDrinkRecipe(id: String): Cocktail {
        return cocktailService.getDrinkRecipe(id).cocktailList.first()
    }

    suspend fun getIngredientInfo(name: String): Ingredient {
        return cocktailService.searchIngredientInfo(name).ingredientList.first()
    }

    fun getFavoriteCocktails() = cocktailDao.getAll()

    fun isFavoriteCocktail(id: String) = cocktailDao.isSaved(id)

    suspend fun toggleFavorite(isFavorite: Boolean, cocktail: Cocktail) {
        if (isFavorite) cocktailDao.delete(cocktail)
        else cocktailDao.insert(cocktail)
    }
}
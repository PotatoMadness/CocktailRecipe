package com.potatomadness.cocktail

import com.potatomadness.cocktail.data.Drink
import com.potatomadness.cocktail.data.FilterType
import com.potatomadness.cocktail.data.SearchQuery
import java.lang.Exception
import javax.inject.Inject

class CocktailRepository @Inject constructor(
    private val cocktailService: CocktailService
){
    suspend fun getFilterList(
        type: FilterType
        ): Result<List<String>> {

        val filterMap = mutableMapOf<String, String>()
        if (type is FilterType.Alcoholic) filterMap["a"] = "list"
        if (type is FilterType.Category) filterMap["c"] = "list"
        if (type is FilterType.Ingredient) filterMap["i"] = "list"
        if (type is FilterType.Glass) filterMap["g"] = "list"

        return try {
            val response = cocktailService.getFilterList(filterMap)
            val categoryList = mutableListOf<String>()
            response.categoryList.mapTo(categoryList) { it.filterName }
            Result.success(categoryList)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getDrinks(query: SearchQuery): List<Drink> {
        return if (query.searchOrFilter) {
            cocktailService.searchDrinksByAlpha(query.query).drinkList
        } else {
            val filter = query.filterType ?: throw Error()
            val filterMap = mutableMapOf<String, String>()
            filterMap[filter.tag] = query.query
            cocktailService.getFilteredDrinks(filterMap).drinkList
        }
    }
}
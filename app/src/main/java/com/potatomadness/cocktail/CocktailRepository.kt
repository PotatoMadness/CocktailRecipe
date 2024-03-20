package com.potatomadness.cocktail

import java.lang.Exception
import javax.inject.Inject

class CocktailRepository @Inject constructor(
    private val cocktailService: CocktailService
){
    suspend fun getFilterList(
        alcoholic: String? = null,
        category: String? = null,
        ingredient: String? = null,
        glass: String? = null,
        ): Result<List<String>> {

        val filterMap = mutableMapOf<String, String>()
        if (!alcoholic.isNullOrBlank()) filterMap["a"] = "list"
        if (!category.isNullOrBlank()) filterMap["c"] = "list"
        if (!ingredient.isNullOrBlank()) filterMap["i"] = "list"
        if (!glass.isNullOrBlank()) filterMap["g"] = "list"

        return try {
            val response = cocktailService.getFilterList(filterMap)
            val categoryList = mutableListOf<String>()
            response.categoryList.mapTo(categoryList) { it.filterName }
            Result.success(categoryList)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
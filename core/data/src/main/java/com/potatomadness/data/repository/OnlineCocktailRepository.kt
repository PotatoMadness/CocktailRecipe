package com.potatomadness.data.repository

import com.potatomadness.data.api.CocktailService
import com.potatomadness.data.dao.CocktailDao
import com.potatomadness.data.dao.IngredientDao
import com.potatomadness.data.model.Cocktail
import com.potatomadness.data.model.FilterType
import com.potatomadness.data.model.Ingredient
import com.potatomadness.data.model.SearchQuery
import javax.inject.Inject

internal class OnlineCocktailRepository @Inject constructor(
    private val cocktailService: CocktailService,
    private val cocktailDao: CocktailDao,
    private val ingredientDao: IngredientDao
) : CocktailRepository {
    override suspend fun getFilterList(
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

    override suspend fun getDrinks(query: SearchQuery): List<Cocktail> {
        return if (query.searchOrFilter) {
            cocktailService.searchDrinksByAlpha(query.query).cocktailList
        } else {
            val filter = query.filterType ?: throw Error()
            val filterMap = mutableMapOf<String, String>()
            filterMap[filter.tag] = query.query
            cocktailService.getFilteredDrinks(filterMap).cocktailList
        }.map { it.toCocktail() }
    }

    override suspend fun getDrinkRecipe(id: Int): Cocktail {
        if (cocktailDao.isExist(id)) return cocktailDao.getRecipeById(id)
        val result = cocktailService.getDrinkRecipe(id).cocktailList.first().toCocktail()
        result.recipeSteps.forEach {
            ingredientDao.insert(Ingredient(name = it.ingName))
        }
        cocktailDao.upsert(cocktail = result)
        return result
    }

    override suspend fun getIngredientInfo(name: String): Ingredient {
        val result = cocktailService.searchIngredientInfo(name).ingredientList.first()
        ingredientDao.insertFullInfo(result)
        return result
    }

    override fun getIngredients() = ingredientDao.getAll()

    override suspend fun isExist(name: String) = cocktailDao.isExist(name)
}

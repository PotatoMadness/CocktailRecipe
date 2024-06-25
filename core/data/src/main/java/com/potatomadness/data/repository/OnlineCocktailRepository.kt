package com.potatomadness.data.repository

import com.potatomadness.database.dao.CocktailDao
import com.potatomadness.database.dao.IngredientDao
import com.potatomadness.model.Cocktail
import com.potatomadness.data.model.FilterType
import com.potatomadness.model.Ingredient
import com.potatomadness.data.model.SearchQuery
import com.potatomadness.database.model.CocktailEntity
import com.potatomadness.database.model.IngredientEntity
import com.potatomadness.database.model.asExternalModel
import com.potatomadness.network.CocktailService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
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

    override fun getCocktailRecipe(id: Int): Flow<Cocktail> = flow {
        if (cocktailDao.isExist(id)) {
            emit(cocktailDao.getRecipeById(id).asExternalModel())
            return@flow
        }
        val resultCocktail = cocktailService.getDrinkRecipe(id).cocktailList.first().toCocktail()
        resultCocktail.recipeSteps.forEach {
            ingredientDao.insert(IngredientEntity(name = it.ingName))
        }
        cocktailDao.upsert(CocktailEntity(resultCocktail))
        emit(resultCocktail)
    }

    override suspend fun getIngredientInfo(name: String): Ingredient {
        val result = cocktailService.searchIngredientInfo(name).ingredientList.first()
        ingredientDao.insertFullInfo(IngredientEntity(result))
        return result
    }

    override fun getIngredients() = ingredientDao.getAll().map {
        it.map { entity -> entity.asExternalModel() }
    }

    override suspend fun isExist(name: String) = cocktailDao.isExist(name)
}

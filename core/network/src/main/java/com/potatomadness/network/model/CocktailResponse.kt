package com.potatomadness.network.model

import com.potatomadness.model.Cocktail
import com.potatomadness.model.Step

data class CocktailResponse (
    var strDrink: String = "",
    var strDrinkThumb: String = "",
    var idDrink: String = "",
    var strCategory: String = "",
    var strAlcoholic: String = "",
    var strGlass: String = "",
    var strInstructions: String = "",
    var strIngredient1: String? = "",
    var strIngredient2: String? = "",
    var strIngredient3: String? = "",
    var strIngredient4: String? = "",
    var strIngredient5: String? = "",
    var strIngredient6: String? = "",
    var strIngredient7: String? = "",
    var strMeasure1: String? = "",
    var strMeasure2: String? = "",
    var strMeasure3: String? = "",
    var strMeasure4: String? = "",
    var strMeasure5: String? = "",
    var strMeasure6: String? = "",
    var strMeasure7: String? = "",
) {
    fun toCocktail(): Cocktail {
        return Cocktail(
            name = strDrink,
            thumbnailUrl = strDrinkThumb,
            id = idDrink.toInt(),
            category = strCategory,
            alcoholic = strAlcoholic,
            glass = strGlass,
            instructions = strInstructions,
            isCustom = false,
            isFavorite = false,
            recipeSteps = getRecipe()
        )
    }

    private fun getRecipe(): List<Step> {
        val stepList: MutableList<Step> = arrayListOf()
        if (strIngredient1.isNullOrEmpty() || strMeasure1.isNullOrEmpty()) return stepList
        stepList.add(Step(strIngredient1!!, strMeasure1!!))
        if (strIngredient2.isNullOrEmpty() || strMeasure2.isNullOrEmpty()) return stepList
        stepList.add(Step(strIngredient2!!, strMeasure2!!))
        if (strIngredient3.isNullOrEmpty() || strMeasure3.isNullOrEmpty()) return stepList
        stepList.add(Step(strIngredient3!!, strMeasure3!!))
        if (strIngredient4.isNullOrEmpty() || strMeasure4.isNullOrEmpty()) return stepList
        stepList.add(Step(strIngredient4!!, strMeasure4!!))
        if (strIngredient5.isNullOrEmpty() || strMeasure5.isNullOrEmpty()) return stepList
        stepList.add(Step(strIngredient5!!, strMeasure5!!))
        if (strIngredient6.isNullOrEmpty() || strMeasure6.isNullOrEmpty()) return stepList
        stepList.add(Step(strIngredient6!!, strMeasure6!!))
        if (strIngredient7.isNullOrEmpty() || strMeasure7.isNullOrEmpty()) return stepList
        stepList.add(Step(strIngredient7!!, strMeasure7!!))
        return stepList
    }
}

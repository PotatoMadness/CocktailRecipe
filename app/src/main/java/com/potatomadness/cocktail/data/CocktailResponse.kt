package com.potatomadness.cocktail.data

import com.google.gson.annotations.SerializedName

data class CocktailResponse (
    @field:SerializedName("strDrink") var name: String,
    @field:SerializedName("strDrinkThumb") val thumbnailUrl: String,
    @field:SerializedName("idDrink") var id: String,
    @field:SerializedName("strCategory") val category: String? = null,
    @field:SerializedName("strAlcoholic") val alcoholic: String? = null,
    @field:SerializedName("strGlass") val glass: String? = null,
    @field:SerializedName("strInstructions") var instructions: String? = null,
    @field:SerializedName("strIngredient1") val _ing1: String? = null,
    @field:SerializedName("strIngredient2") val _ing2: String? = null,
    @field:SerializedName("strIngredient3") val _ing3: String? = null,
    @field:SerializedName("strIngredient4") val _ing4: String? = null,
    @field:SerializedName("strIngredient5") val _ing5: String? = null,
    @field:SerializedName("strIngredient6") val _ing6: String? = null,
    @field:SerializedName("strIngredient7") val _ing7: String? = null,
    @field:SerializedName("strMeasure1") val _measure1: String? = null,
    @field:SerializedName("strMeasure2") val _measure2: String? = null,
    @field:SerializedName("strMeasure3") val _measure3: String? = null,
    @field:SerializedName("strMeasure4") val _measure4: String? = null,
    @field:SerializedName("strMeasure5") val _measure5: String? = null,
    @field:SerializedName("strMeasure6") val _measure6: String? = null,
    @field:SerializedName("strMeasure7") val _measure7: String? = null,
) {
    fun toCocktail(): Cocktail {
        return Cocktail(
            name = name,
            thumbnailUrl = thumbnailUrl,
            id = id.toInt(),
            category = category,
            alcoholic = alcoholic,
            glass = glass,
            instructions = instructions,
            isCustom = false,
            isFavorite = false,
            recipeSteps = getRecipe()
        )
    }

    private fun getRecipe(): List<Step> {
        val stepList: MutableList<Step> = arrayListOf()
        if (_ing1.isNullOrEmpty() || _measure1.isNullOrEmpty()) return stepList
        stepList.add(Step(_ing1, _measure1))
        if (_ing2.isNullOrEmpty() || _measure2.isNullOrEmpty()) return stepList
        stepList.add(Step(_ing2, _measure2))
        if (_ing3.isNullOrEmpty() || _measure3.isNullOrEmpty()) return stepList
        stepList.add(Step(_ing3, _measure3))
        if (_ing4.isNullOrEmpty() || _measure4.isNullOrEmpty()) return stepList
        stepList.add(Step(_ing4, _measure4))
        if (_ing5.isNullOrEmpty() || _measure5.isNullOrEmpty()) return stepList
        stepList.add(Step(_ing5, _measure5))
        if (_ing6.isNullOrEmpty() || _measure6.isNullOrEmpty()) return stepList
        stepList.add(Step(_ing6, _measure6))
        if (_ing7.isNullOrEmpty() || _measure7.isNullOrEmpty()) return stepList
        stepList.add(Step(_ing7, _measure7))
        return stepList
    }
}

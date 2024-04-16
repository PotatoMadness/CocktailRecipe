package com.potatomadness.cocktail.data

import com.google.gson.annotations.SerializedName

data class Cocktail(
    @field:SerializedName("strDrink") val name: String,
    @field:SerializedName("strDrinkThumb") val thumbnailUrl: String,
    @field:SerializedName("idDrink") val id: String,
    @field:SerializedName("strCategory") val category: String? = null,
    @field:SerializedName("strAlcoholic") val alcoholic: String? = null,
    @field:SerializedName("strGlass") val glass: String? = null,
    @field:SerializedName("strInstructions") val instructions: String? = null,
    @field:SerializedName("strIngredient1") private val _ing1: String? = null,
    @field:SerializedName("strIngredient2") private val _ing2: String? = null,
    @field:SerializedName("strIngredient3") private val _ing3: String? = null,
    @field:SerializedName("strIngredient4") private val _ing4: String? = null,
    @field:SerializedName("strIngredient5") private val _ing5: String? = null,
    @field:SerializedName("strIngredient6") private val _ing6: String? = null,
    @field:SerializedName("strIngredient7") private val _ing7: String? = null,
    @field:SerializedName("strMeasure1") private val _measure1: String? = null,
    @field:SerializedName("strMeasure2") private val _measure2: String? = null,
    @field:SerializedName("strMeasure3") private val _measure3: String? = null,
    @field:SerializedName("strMeasure4") private val _measure4: String? = null,
    @field:SerializedName("strMeasure5") private val _measure5: String? = null,
    @field:SerializedName("strMeasure6") private val _measure6: String? = null,
    @field:SerializedName("strMeasure7") private val _measure7: String? = null,
) {
    val recipeSteps: List<Pair<String, String>>
        get() {
            val stepList: MutableList<Pair<String, String>> = arrayListOf()
            if (_ing1.isNullOrEmpty() || _measure1.isNullOrEmpty()) return stepList
            stepList.add(Pair(_ing1, _measure1))
            if (_ing2.isNullOrEmpty() || _measure2.isNullOrEmpty()) return stepList
            stepList.add(Pair(_ing2, _measure2))
            if (_ing3.isNullOrEmpty() || _measure3.isNullOrEmpty()) return stepList
            stepList.add(Pair(_ing3, _measure3))
            if (_ing4.isNullOrEmpty() || _measure4.isNullOrEmpty()) return stepList
            stepList.add(Pair(_ing4, _measure4))
            if (_ing5.isNullOrEmpty() || _measure5.isNullOrEmpty()) return stepList
            stepList.add(Pair(_ing5, _measure5))
            if (_ing6.isNullOrEmpty() || _measure6.isNullOrEmpty()) return stepList
            stepList.add(Pair(_ing6, _measure6))
            if (_ing7.isNullOrEmpty() || _measure7.isNullOrEmpty()) return stepList
            stepList.add(Pair(_ing7, _measure7))
            return stepList
        }
}
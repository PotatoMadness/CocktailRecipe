package com.potatomadness.cocktail.data

import com.google.gson.annotations.SerializedName

data class Ingredient(
    @field:SerializedName("strIngredient") val name: String,
    @field:SerializedName("strDescription") val description: String,
    @field:SerializedName("strType") val type: String,
    @field:SerializedName("strAlcohol") val strAlcohol: String,
    @field:SerializedName("strABV") val aBV: String,
    @field:SerializedName("idIngredient") val id: String,
) {
    val isAlcohol: Boolean = strAlcohol == "Yes"
}

val String.ingredientImageUrl: String
    get() = "https://www.thecocktaildb.com/images/ingredients/$this-small.png"


package com.potatomadness.cocktail.data

import com.google.gson.annotations.SerializedName

data class Ingredient(
    @field:SerializedName("strIngredient") val name: String,
    @field:SerializedName("strDescription") val description: String?,
    @field:SerializedName("strType") val type: String?,
    @field:SerializedName("strAlcohol") val strAlcohol: String,
    @field:SerializedName("strABV") val strABV: String?,
    @field:SerializedName("idIngredient") val id: String,
) {
    val aBV: String
        get() = if (strAlcohol == "Yes") strABV ?: "None" else "None"
}

val String.ingredientThumbNailImageUrl: String
    get() = "https://www.thecocktaildb.com/images/ingredients/$this-Small.png"

val String.ingredientImageUrl: String
    get() = "https://www.thecocktaildb.com/images/ingredients/$this-Medium.png"


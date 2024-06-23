package com.potatomadness.model

data class Ingredient(
    val name: String,
    val description: String? = null,
    val type: String? = null,
    val strAlcohol: String? = null,
    val strABV: String? = null,
) {
    val aBV: String
        get() = if (strAlcohol == "Yes") strABV ?: "None" else "None"
}

val String.ingredientThumbNailImageUrl: String
    get() = "https://www.thecocktaildb.com/images/ingredients/$this-Small.png"

val String.ingredientImageUrl: String
    get() = "https://www.thecocktaildb.com/images/ingredients/$this-Medium.png"


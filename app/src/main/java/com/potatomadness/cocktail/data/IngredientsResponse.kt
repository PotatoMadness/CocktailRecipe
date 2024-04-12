package com.potatomadness.cocktail.data

import com.google.gson.annotations.SerializedName

data class IngredientsResponse(
    @field:SerializedName("ingredients") val ingredientList: List<Ingredient>
)
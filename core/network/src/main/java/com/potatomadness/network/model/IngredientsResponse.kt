package com.potatomadness.network.model

import com.google.gson.annotations.SerializedName
import com.potatomadness.model.Ingredient

data class IngredientsResponse(
    @field:SerializedName("ingredients") val ingredientList: List<Ingredient>
)
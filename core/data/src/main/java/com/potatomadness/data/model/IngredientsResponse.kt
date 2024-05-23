package com.potatomadness.data.model

import com.google.gson.annotations.SerializedName

data class IngredientsResponse(
    @field:SerializedName("ingredients") val ingredientList: List<com.potatomadness.data.model.Ingredient>
)
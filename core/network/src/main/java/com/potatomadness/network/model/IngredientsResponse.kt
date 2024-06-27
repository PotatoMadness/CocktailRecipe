package com.potatomadness.network.model

import com.google.gson.annotations.SerializedName
import com.potatomadness.model.Ingredient

data class IngredientsResponse(
    @field:SerializedName("ingredients") val ingredientList: List<IngredientResponse>
)
data class IngredientResponse (
    var strIngredient: String = "",
    var strDescription: String = "",
    var idIngredient: String = "",
    var strType: String = "",
    var strAlcohol: String = "",
    var strABV: String = "") {

    fun toIngredient(): Ingredient = Ingredient(
        name = strIngredient,
        description = strDescription,
        type = strType,
        strAlcohol = strAlcohol,
        strABV = strABV
    )
}
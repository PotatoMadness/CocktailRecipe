package com.potatomadness.cocktail.data

import com.google.gson.annotations.SerializedName

data class DrinksResponse(
    @field:SerializedName("drinks") val glassList: List<Drinks>
)
data class Drinks(
    @field:SerializedName("strDrink") val name: String,
    @field:SerializedName("strDrinkThumb") val thumbnailUrl: String,
    @field:SerializedName("idDrink") val id: String,
)
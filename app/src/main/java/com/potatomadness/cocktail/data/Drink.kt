package com.potatomadness.cocktail.data

import com.google.gson.annotations.SerializedName

data class Drink(
    @field:SerializedName("strDrink") val name: String,
    @field:SerializedName("strDrinkThumb") val thumbnailUrl: String,
    @field:SerializedName("idDrink") val id: String,
)
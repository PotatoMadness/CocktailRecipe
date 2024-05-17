package com.potatomadness.cocktail.data

import com.google.gson.annotations.SerializedName

data class DrinksResponse(
    @field:SerializedName("drinks") val cocktailList: List<CocktailResponse>
)

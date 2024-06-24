package com.potatomadness.network.model

import com.google.gson.annotations.SerializedName

data class DrinksResponse(
    @field:SerializedName("drinks") val cocktailList: List<CocktailResponse>
)

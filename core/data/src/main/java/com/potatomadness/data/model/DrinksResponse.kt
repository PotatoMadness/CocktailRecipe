package com.potatomadness.data.model

import com.google.gson.annotations.SerializedName

data class DrinksResponse(
    @field:SerializedName("drinks") val cocktailList: List<CocktailResponse>
)

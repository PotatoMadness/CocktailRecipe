package com.potatomadness.cocktail.data

import com.google.gson.annotations.SerializedName

data class FilterListResponse (@field:SerializedName("drinks") val categoryList: List<StringFilter>)

data class StringFilter(
    @field:SerializedName("strCategory", alternate = ["strGlass", "strIngredient1", "strAlcoholic"])
    val filterName: String
)
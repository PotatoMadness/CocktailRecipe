package com.potatomadness.model

data class Cocktail(
    var name: String,
    val thumbnailUrl: String,
    var id: Int = 0,
    val category: String? = null,
    val alcoholic: String? = null,
    val glass: String? = null,
    var instructions: String? = null,
    val isCustom: Boolean = false,
    val isFavorite: Boolean = false,
    val recipeSteps: List<Step> = listOf()
)

data class Step(
    val ingName: String,
    val amount: String,
    val aBV: String = ""
)
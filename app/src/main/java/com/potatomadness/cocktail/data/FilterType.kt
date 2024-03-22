package com.potatomadness.cocktail.data

sealed class FilterType(val tag: String) {
    object Alcoholic: FilterType("a")
    object Category: FilterType("c")
    object Glass: FilterType("g")
    object Ingredient: FilterType("i")
}
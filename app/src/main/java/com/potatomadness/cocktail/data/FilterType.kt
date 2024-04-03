package com.potatomadness.cocktail.data

sealed class FilterType(val tag: String, val tagName: String) {
    object Alcoholic: FilterType("a", "주종")
    object Category: FilterType("c", "분류")
    object Glass: FilterType("g", "잔 타입")
    object Ingredient: FilterType("i", "재료")
}
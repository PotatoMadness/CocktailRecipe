package com.potatomadness.cocktail.data

data class SearchQuery (
    var filterType: FilterType? = null,
    var query: String
) {
    // TODO :: 알파벳 검색, 이름 검색 타입
    var searchOrFilter: Boolean = filterType == null
}
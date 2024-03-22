package com.potatomadness.cocktail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.potatomadness.cocktail.data.Drinks
import com.potatomadness.cocktail.data.FilterType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CocktailViewModel @Inject constructor(
    val cocktailRepository: CocktailRepository
): ViewModel() {

    val filterType = MutableStateFlow<FilterType>(FilterType.Alcoholic)

    private val _filterList = MutableStateFlow<List<String>>(listOf())
    val filterList = _filterList.asStateFlow()

    private val _filteredDrinks = MutableStateFlow<List<Drinks>>(listOf())
    val filteredDrinks = _filteredDrinks.asStateFlow()

    init {
        getFilterList()
    }

    fun getFilterList() {
        viewModelScope.launch {
            cocktailRepository.getFilterList(filterType.value).onSuccess {
                _filterList.emit(it)
            }
        }
    }

    fun getDrinkList(name: String) {
        viewModelScope.launch {
            cocktailRepository.getDrinks(filterType.value, name).onSuccess {
                _filteredDrinks.emit(it)
            }
        }
    }
}
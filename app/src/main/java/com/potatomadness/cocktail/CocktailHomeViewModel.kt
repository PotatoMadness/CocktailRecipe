package com.potatomadness.cocktail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.potatomadness.cocktail.data.Cocktail
import com.potatomadness.cocktail.data.FilterType
import com.potatomadness.cocktail.data.SearchQuery
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CocktailViewModel @Inject constructor(
    val cocktailRepository: CocktailRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(CocktailHomeUIState())
    val uiState: StateFlow<CocktailHomeUIState> = _uiState

    val filterType = MutableStateFlow<FilterType>(FilterType.Alcoholic)

    private val _filterList = MutableStateFlow<List<String>>(listOf())
    val filterList = _filterList.asStateFlow()

    init {
        getFilterList()
    }

    fun closeListScreen() {
        _uiState.value = _uiState.value.copy(cocktailList = null)
    }

    fun getFilterList() {
        viewModelScope.launch {
            _filterList.emit(cocktailRepository.getFilterList(filterType.value))
        }
    }

    fun changeFilterType(type: FilterType) {
        viewModelScope.launch {
            filterType.emit(type)
            getFilterList()
        }
    }

    fun getDrinkListByFilter(filter: String) {
        viewModelScope.launch {
            val newDrinks =
                cocktailRepository.getDrinks(
                    SearchQuery(filterType = filterType.value,
                        query = filter)
                )
            _uiState.value = _uiState.value.copy(cocktailList = newDrinks)
        }
    }

    fun searchDrinkListByAlpha(alpha: String) {
        viewModelScope.launch {
            val newDrinks =
                cocktailRepository.getDrinks(
                    SearchQuery(filterType = null,
                        query = alpha)
                )
            _uiState.value = _uiState.value.copy(cocktailList = newDrinks)
        }
    }
}

data class CocktailHomeUIState (
    // 선택된 필터타입, 서브필터 리스트도 빼야함
    val cocktailList: List<Cocktail>? = null,
)
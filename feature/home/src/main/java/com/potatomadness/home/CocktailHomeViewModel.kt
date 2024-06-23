package com.potatomadness.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.potatomadness.model.Cocktail
import com.potatomadness.data.model.FilterType
import com.potatomadness.data.model.SearchQuery
import com.potatomadness.data.repository.CocktailRepository
import com.potatomadness.domain.GetPopularRecipeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CocktailViewModel @Inject constructor(
    private val cocktailRepository: CocktailRepository,
    getPopularRecipeUseCase: GetPopularRecipeUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(CocktailHomeUIState())
    val uiState: StateFlow<CocktailHomeUIState> = _uiState

    val filterType = MutableStateFlow<FilterType>(FilterType.Alcoholic)

    private val _filterList = MutableStateFlow<List<String>>(listOf())
    val filterList = _filterList.asStateFlow()

    val popularList = getPopularRecipeUseCase.recipeFlow

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
    val popularList: List<Cocktail>? = null,
) {
    fun getPanelType(isExpanded: Boolean): ScreenType {
        val hasCocktailList = !this.cocktailList.isNullOrEmpty()
        return when(isExpanded) {
            true ->
                if (hasCocktailList) ScreenType.FilterWithList else ScreenType.Filters

            false ->
                if (hasCocktailList) ScreenType.List else ScreenType.Filters
        }
    }
}
enum class ScreenType {
    Filters, FilterWithList, List
}
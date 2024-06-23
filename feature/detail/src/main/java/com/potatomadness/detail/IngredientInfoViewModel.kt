package com.potatomadness.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.potatomadness.data.repository.CocktailRepository
import com.potatomadness.model.Ingredient
import com.potatomadness.detail.navigation.IngredientRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IngredientInfoViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    cocktailRepository: CocktailRepository
): ViewModel() {
    val ingredientName: String = savedStateHandle.get<String>(IngredientRoute.ingredientName)!!

    private val _uiState = MutableStateFlow(InfoUiState())
    val uiState: StateFlow<InfoUiState> = _uiState

    init {
        viewModelScope.launch {
            val drink = cocktailRepository.getIngredientInfo(ingredientName)
            _uiState.value = _uiState.value.copy(
                ingredient = drink
            )
        }
    }
}
data class InfoUiState (
    val ingredient: Ingredient? = null,
)
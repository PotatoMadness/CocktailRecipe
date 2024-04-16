package com.potatomadness.cocktail.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.potatomadness.cocktail.CocktailRepository
import com.potatomadness.cocktail.data.Ingredient
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
    val ingredientName: String = savedStateHandle.get<String>(INGREDIENT_NAME_SAVED_STATE_KEY)!!

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
    companion object {
        private const val INGREDIENT_NAME_SAVED_STATE_KEY = "ingredientName"
    }
}
data class InfoUiState (
    val ingredient: Ingredient? = null,
)
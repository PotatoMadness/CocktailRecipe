package com.potatomadness.cocktail.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.potatomadness.cocktail.CocktailRepository
import com.potatomadness.cocktail.data.Cocktail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrinkDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    cocktailRepository: CocktailRepository
): ViewModel() {
    val drinkId: String = savedStateHandle.get<String>(DRINK_ID_SAVED_STATE_KEY)!!

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState

    init {
        viewModelScope.launch {
            val drink = cocktailRepository.getDrinkRecipe(drinkId)
            _uiState.value = _uiState.value.copy(
                cocktail = drink
            )
        }
    }
    companion object {
        private const val DRINK_ID_SAVED_STATE_KEY = "drinkId"
    }
}
data class DetailUiState (
    val cocktail: Cocktail? = null,
)
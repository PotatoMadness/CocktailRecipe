package com.potatomadness.cocktail.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.potatomadness.data.repository.CocktailRepository
import com.potatomadness.cocktail.Const.COCKTAIL_ID_SAVED_STATE_KEY
import com.potatomadness.data.model.Cocktail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrinkDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val cocktailRepository: CocktailRepository
): ViewModel() {
    val cocktailId: Int = savedStateHandle.get<Int>(COCKTAIL_ID_SAVED_STATE_KEY)!!
    val isFavorite = cocktailRepository.isFavoriteCocktail(cocktailId)

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState

    init {
        viewModelScope.launch {
            val drink = cocktailRepository.getDrinkRecipe(cocktailId)
            _uiState.value = _uiState.value.copy(
                cocktail = drink
            )
        }
    }

    fun toggleFavorite(isFavorite: Boolean) {
        uiState.value.cocktail?.let {
            viewModelScope.launch {
                cocktailRepository.toggleFavorite(isFavorite, it)
            }
        }
    }
}
data class DetailUiState (
    val cocktail: Cocktail? = null,
)
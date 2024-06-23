package com.potatomadness.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.potatomadness.data.repository.CocktailRepository
import com.potatomadness.model.Cocktail
import com.potatomadness.data.repository.FavoriteRepository
import com.potatomadness.detail.navigation.RecipeRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrinkDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val cocktailRepository: CocktailRepository,
    private val favoriteRepository: FavoriteRepository
): ViewModel() {
    private val cocktailId: Int = savedStateHandle.get<Int>(RecipeRoute.cocktailId)!!
    val isFavorite = favoriteRepository.isFavoriteCocktail(cocktailId)

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
                favoriteRepository.toggleFavorite(isFavorite, it)
            }
        }
    }
}
data class DetailUiState (
    val cocktail: Cocktail? = null,
)
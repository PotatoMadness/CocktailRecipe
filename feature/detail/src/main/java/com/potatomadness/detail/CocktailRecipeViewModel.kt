package com.potatomadness.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.potatomadness.data.repository.CocktailRepository
import com.potatomadness.model.Cocktail
import com.potatomadness.data.repository.FavoriteRepository
import com.potatomadness.detail.navigation.RecipeRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrinkDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val cocktailRepository: CocktailRepository,
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {
    private val cocktailId: Int = savedStateHandle.get<Int>(RecipeRoute.cocktailId)!!

    val uiState: StateFlow<DetailUiState> = detailUiState(cocktailId, cocktailRepository, favoriteRepository)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), initialValue = DetailUiState.Loading)

    private fun detailUiState(
        cocktailId: Int,
        cocktailRepository: CocktailRepository,
        favoriteRepository: FavoriteRepository,
    ): Flow<DetailUiState> {
        val cocktailRecipe = cocktailRepository.getCocktailRecipe(cocktailId)
        val isFavorite = favoriteRepository.isFavoriteCocktail(cocktailId)
        return combine(isFavorite, cocktailRecipe, ::Pair).map {
            val (favorite, cocktail) = it
            DetailUiState.Success(isFavorite = favorite, cocktail = cocktail)
        }.catch {
            DetailUiState.Failed
        }
    }

    fun toggleFavorite(isFavorite: Boolean, cocktailId: Int) {
        viewModelScope.launch {
            favoriteRepository.toggleFavorite(isFavorite, cocktailId)
        }
    }
}

sealed interface DetailUiState {
    object Loading : DetailUiState
    object Failed : DetailUiState
    data class Success(val cocktail: Cocktail, val isFavorite: Boolean) : DetailUiState
}
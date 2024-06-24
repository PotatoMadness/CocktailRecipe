package com.potatomadness.favorites

import androidx.lifecycle.ViewModel
import com.potatomadness.data.repository.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteRecipeViewModel @Inject constructor(
    favoriteRepository: FavoriteRepository
): ViewModel() {
    val favoriteList = favoriteRepository.getFavoriteCocktails()
}
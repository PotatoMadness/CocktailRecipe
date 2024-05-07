package com.potatomadness.cocktail.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.potatomadness.cocktail.CocktailRepository
import com.potatomadness.cocktail.Const.COCKTAIL_ID_SAVED_STATE_KEY
import com.potatomadness.cocktail.data.Cocktail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyRecipeCreateViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    cocktailRepository: CocktailRepository
): ViewModel() {
    val cocktailId: String = savedStateHandle.get<String>(COCKTAIL_ID_SAVED_STATE_KEY)?: ""
    private val _newCocktail: MutableStateFlow<Cocktail> = MutableStateFlow(Cocktail(name = "", thumbnailUrl = "", id = ""))
    val newCocktail: StateFlow<Cocktail> = _newCocktail
    val isValid: StateFlow<Boolean> = _newCocktail.map {
        (it.recipeSteps.isNotEmpty()
            && !it.instructions.isNullOrEmpty()
            && !it.name.isNullOrEmpty())
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    init {
        if(!cocktailId.isNullOrBlank()) {
            viewModelScope.launch {
                val copyFrom = cocktailRepository.getDrinkRecipe(cocktailId)
                _newCocktail.emit(copyFrom)
            }
        }
    }

    fun updateCocktailName(name: String) {
        viewModelScope.launch {
            _newCocktail.emit(_newCocktail.value.copy(name = name))
        }
    }

    fun updateCocktailInstruction(contents: String) {
        viewModelScope.launch {
            _newCocktail.emit(_newCocktail.value.copy(instructions = contents))
        }
    }
}
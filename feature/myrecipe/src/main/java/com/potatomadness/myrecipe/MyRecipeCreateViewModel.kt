package com.potatomadness.myrecipe

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.potatomadness.data.model.Cocktail
import com.potatomadness.data.repository.CocktailRepository
import com.potatomadness.data.model.Step
import com.potatomadness.myrecipe.navigation.MyRecipeRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyRecipeCreateViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val cocktailRepository: CocktailRepository
): ViewModel() {
    val cocktailId: Int = savedStateHandle.get<Int>(MyRecipeRoute.cocktailId)?: -1
    private val _newCocktail: MutableStateFlow<Cocktail> = MutableStateFlow(
        Cocktail(
            name = "",
            thumbnailUrl = "",
            recipeSteps = listOf(),
            isCustom = true
        )
    )
    val newCocktail: StateFlow<Cocktail> = _newCocktail
    private val _isExist = _newCocktail.map { cocktailRepository.isExist(it.name) }
    val isValid: StateFlow<Boolean> = combine(_newCocktail, _isExist) { cocktail, isExist ->
        (!cocktail.recipeSteps.isNullOrEmpty()
            && !cocktail.instructions.isNullOrEmpty()
            && !cocktail.name.isNullOrEmpty()
            && !isExist)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    val ingredients = cocktailRepository.getIngredients().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())

    fun addStep(step: Step) {
        viewModelScope.launch {
            val steps: MutableList<Step> = _newCocktail.value.recipeSteps.toMutableList().apply { add(step) }
            _newCocktail.emit(_newCocktail.value.copy(recipeSteps = steps.toList()))
        }
    }

    init {
        if(cocktailId > 0) {
            viewModelScope.launch {
                val copyFrom = cocktailRepository.getDrinkRecipe(cocktailId)
                _newCocktail.emit(copyFrom.copy(isCustom = true, id = 0))
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

    fun createNewRecipe() {
        viewModelScope.launch {
            cocktailRepository.createNewRecipe(_newCocktail.value)
        }
    }
}
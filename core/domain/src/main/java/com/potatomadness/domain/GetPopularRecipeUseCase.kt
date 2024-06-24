package com.potatomadness.domain

import com.potatomadness.data.repository.PopularRecipeRepository
import javax.inject.Inject

class GetPopularRecipeUseCase @Inject constructor(
    popularRecipeRepository: PopularRecipeRepository
) {
    val recipeFlow = popularRecipeRepository.popularRecipeFlow
}
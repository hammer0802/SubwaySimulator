package com.hammer.app.subwaysimulator.ui.top

import androidx.lifecycle.ViewModel
import com.hammer.app.subwaysimulator.repository.RecipeStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TopViewModel @Inject constructor(
    recipeStorage: RecipeStorage
) : ViewModel() {
    val recipeList = recipeStorage.getSortedRecipeList()
}

package com.hammer.app.subwaysimulator.repository

import android.content.Context
import androidx.core.content.edit
import com.hammer.app.subwaysimulator.model.Recipe
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipeStorage @Inject constructor(
    @ApplicationContext context: Context
) {
    private val pref = context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE)

    fun getSortedRecipeList(): List<Recipe> {
        return pref.all.values.filterIsInstance(String::class.java).map { value ->
            Json.decodeFromString(Recipe.serializer(), value)
        }.toList().sortedBy { it.createTime }
    }

    fun setRecipe(recipe: Recipe) {
        pref.edit {
            putString(recipe.recipeId.id, Json.encodeToString(Recipe.serializer(), recipe))
        }
    }

    companion object {
        private const val PREF_KEY = "recipe"
    }
}

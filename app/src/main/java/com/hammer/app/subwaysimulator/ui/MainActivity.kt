package com.hammer.app.subwaysimulator.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.ExperimentalMaterialApi
import com.hammer.app.subwaysimulator.model.Recipe
import com.hammer.app.subwaysimulator.ui.top.TopScreen
import kotlinx.serialization.json.Json

@ExperimentalMaterialApi
class MainActivity : AppCompatActivity() {
    private val preference: SharedPreferences by lazy { getSharedPreferences("recipe", Context.MODE_PRIVATE) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        TutorialActivity.showIfNeeded(this, savedInstanceState)

        val recipeList = preference.all.values.filterIsInstance(String::class.java).map { value ->
            Json.decodeFromString(Recipe.serializer(), value)
        }.toList().sortedBy { it.createTime }
        setContent {
            TopScreen(recipeList)
        }
    }
}
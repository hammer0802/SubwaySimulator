package com.hammer.app.subwaysimulator.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.hammer.app.subwaysimulator.ui.top.TopViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val topViewModel: TopViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        TutorialActivity.showIfNeeded(this, savedInstanceState)

        setContent {
            val navController = rememberNavController()
            MyAppNavHost(navHostController = navController)
        }

        topViewModel.navigationEvent.onEach { ev ->
            when (ev) {
                TopViewModel.Nav.OpenCreateRecipeScreen -> {
                    CreateRecipeActivity.start(this@MainActivity)
                }
                TopViewModel.Nav.OpenPrivacyPolicy -> {
                    val uri = Uri.parse("https://hammer-appli.hatenablog.com/entry/2018/11/24/131136")
                    val intentToPolicy = Intent(Intent.ACTION_VIEW, uri)
                    startActivity(intentToPolicy)
                }
                is TopViewModel.Nav.OpenRecipeDetailScreen -> {
                    val intentToResult = Intent(this@MainActivity, RecipeResultActivity::class.java)
                    intentToResult.putExtra("key", ev.recipe.recipeId.id)
                    startActivity(intentToResult)
                }
                TopViewModel.Nav.OpenTutorial -> TutorialActivity.showForcibly(this@MainActivity)
            }
        }.launchIn(lifecycleScope)
    }
}

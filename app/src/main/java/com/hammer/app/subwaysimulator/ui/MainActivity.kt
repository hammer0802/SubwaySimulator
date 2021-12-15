package com.hammer.app.subwaysimulator.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.navigation.compose.rememberNavController
import com.hammer.app.subwaysimulator.ui.tutorial.TutorialActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        TutorialActivity.showIfNeeded(this, savedInstanceState)

        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                MyAppNavHost(navHostController = navController)
            }
        }
    }
}

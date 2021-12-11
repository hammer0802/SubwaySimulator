package com.hammer.app.subwaysimulator.ui

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hammer.app.subwaysimulator.ui.edit.EditScreen
import com.hammer.app.subwaysimulator.ui.top.TopScreen
import com.hammer.app.subwaysimulator.ui.top.TopViewModel

@ExperimentalMaterialApi
@Composable
fun MyAppNavHost(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = MyAppDestination.TOP.name
    ) {
        composable(route = MyAppDestination.TOP.name) {
            val topViewModel = hiltViewModel<TopViewModel>()
            TopScreen(topViewModel = topViewModel)
        }
        composable(route = MyAppDestination.EDIT_RECIPE.name) {
            EditScreen()
        }
    }
}

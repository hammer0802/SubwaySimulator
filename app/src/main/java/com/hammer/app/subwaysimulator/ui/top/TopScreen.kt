package com.hammer.app.subwaysimulator.ui.top

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.hammer.app.subwaysimulator.R
import com.hammer.app.subwaysimulator.model.Recipe

@ExperimentalMaterialApi
@Composable
fun TopScreen(recipeList: List<Recipe>, topViewModel: TopViewModel = viewModel()) {
    var showMenu by remember { mutableStateOf(false) }

    MaterialTheme {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White),
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = stringResource(id = R.string.app_name))
                    },
                    actions = {
                        IconButton(onClick = { showMenu = true }) {
                            Icon(Icons.Filled.Menu, "menu")
                        }
                        DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                            DropdownMenuItem(onClick = {
                                topViewModel.openTutorial()
                                // TutorialActivity.showForcibly(activity)
                                showMenu = false
                            }) {
                                Text(text = stringResource(id = R.string.action_settings))
                            }
                            DropdownMenuItem(onClick = {
                                topViewModel.openPrivacyPolicy()
                                // val uri = Uri.parse("https://hammer-appli.hatenablog.com/entry/2018/11/24/131136")
                                // val intentToPolicy = Intent(Intent.ACTION_VIEW, uri)
                                // startActivity(activity, intentToPolicy, null)
                                showMenu = false
                            }) {
                                Text(text = stringResource(id = R.string.action_policy))
                            }
                        }
                    },
                    backgroundColor = colorResource(id = R.color.colorPrimary),
                    contentColor = Color.White,
                    elevation = 12.dp
                )
            },
            content = {
                Column {
                    LazyColumn(
                        modifier = Modifier.weight(1F),
                        contentPadding = PaddingValues(all = 8.dp)
                    ) {
                        items(items = recipeList) { recipe ->
                            ListCard(recipe = recipe, topViewModel = topViewModel)
                        }
                    }
                    AndroidView(
                        modifier = Modifier
                            .fillMaxWidth(),
                        factory = { context ->
                            val adView = AdView(context)
                            adView.adSize = AdSize.BANNER
                            adView.adUnitId = context.getString(R.string.ad_unit_id)
                            adView.loadAd(AdRequest.Builder().build())
                            adView
                        },
                    )
                }
            },
            floatingActionButtonPosition = FabPosition.End,
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        topViewModel.openCreateRecipeScreen()
                        // CreateRecipeActivity.start(activity)
                    },
                    backgroundColor = colorResource(id = R.color.colorAccent),
                    contentColor = Color.White,
                ) {
                    Icon(Icons.Filled.Add, "create recipe")
                }
            }
        )
    }
}

@ExperimentalMaterialApi
@Composable
private fun ListCard(recipe: Recipe, topViewModel: TopViewModel) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 4.dp,
        onClick = {
            topViewModel.openRecipeDetailScreen()
            // val intentToResult = Intent(activity, RecipeResultActivity::class.java)
            // intentToResult.putExtra("key", recipe.recipeId.id)
            // activity.startActivity(intentToResult)
        }
    ) {
        Row(
            modifier = Modifier.padding(4.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = recipe.name)
                Text(text = recipe.sandwich.type.sandName)
            }
            Text(text = "￥${recipe.price}")
        }
    }
}

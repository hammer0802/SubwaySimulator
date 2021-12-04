package com.hammer.app.subwaysimulator.ui.top

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.hammer.app.subwaysimulator.R

@Composable
fun TopScreen() {
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
                            DropdownMenuItem(onClick = { showMenu = false }) {
                                Text(text = stringResource(id = R.string.action_settings))
                            }
                            DropdownMenuItem(onClick = { showMenu = false }) {
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
                    LazyColumn(modifier = Modifier.weight(1F)) {
                        items(100) { item ->
                            Text("item: $item")
                            Divider(thickness = 0.8.dp)
                        }
                    }
                }
            },
            floatingActionButtonPosition = FabPosition.End,
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {},
                    backgroundColor = colorResource(id = R.color.colorAccent),
                    contentColor = Color.White,
                ) {
                    Icon(Icons.Filled.Add, "")
                }
            }
        )
    }
}

@Preview
@Composable
fun PreviewTopScreen() {
    TopScreen()
}
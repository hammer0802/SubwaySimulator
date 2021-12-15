package com.hammer.app.subwaysimulator.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hammer.app.subwaysimulator.R
import com.hammer.app.subwaysimulator.ui.common.AdView
import com.hammer.app.subwaysimulator.ui.common.TextInColoredBox
import com.hammer.app.subwaysimulator.ui.common.ToppingText

@Composable
fun RecipeDetailScreen() {
    var showMenu by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.recipe_detail))
                },
                actions = {
                    IconButton(onClick = { showMenu = true }) {
                        Icon(Icons.Filled.Menu, "menu")
                    }
                    DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                        DropdownMenuItem(onClick = {
                            showMenu = false
                        }) {
                            Text(text = stringResource(id = R.string.action_edit))
                        }
                        DropdownMenuItem(onClick = {
                            showMenu = false
                        }) {
                            Text(text = stringResource(id = R.string.action_sns))
                        }
                        DropdownMenuItem(onClick = {
                            showMenu = false
                        }) {
                            Text(text = stringResource(id = R.string.action_save_image))
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
                Column(
                    modifier = Modifier
                        .weight(1F)
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min)
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.oldpaper),
                            contentDescription = "background",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "レシピ名",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 17.sp,
                                )
                            }
                            TextInColoredBox(text = stringResource(id = R.string.step1))
                            Text(text = "サンドウィッチ")
                            TextInColoredBox(text = stringResource(id = R.string.step2))
                            Text(text = "パン")
                            TextInColoredBox(text = stringResource(id = R.string.step3))
                            Text(text = "トッピング")
                            TextInColoredBox(text = stringResource(id = R.string.step4))
                            Text(text = "野菜")
                            ToppingText()
                            Text(text = "アクセント野菜")
                            TextInColoredBox(text = stringResource(id = R.string.step5))
                            Text(text = "ドレッシング")
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp, top = 32.dp),
                                contentAlignment = Alignment.CenterEnd
                            ) {
                                Text(
                                    text = "金額",
                                    modifier = Modifier.padding(horizontal = 24.dp),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 17.sp,
                                )
                            }
                        }
                    }
                }
                AdView()
            }
        },
    )
}

@Preview
@Composable
fun PreviewRecipeDetailScreen() {
    RecipeDetailScreen()
}

package com.hammer.app.subwaysimulator.ui.top

import android.graphics.Paint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TopScreen() {
    MaterialTheme {
        Box(modifier = Modifier.fillMaxSize().background(color = Color.White)) {
            Text(text = "Hello, Compose!", modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Preview
@Composable
fun PreviewTopScreen() {
    TopScreen()
}
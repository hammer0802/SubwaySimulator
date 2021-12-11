package com.hammer.app.subwaysimulator.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hammer.app.subwaysimulator.R

@Composable
fun TextInColoredBox(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(color = colorResource(id = R.color.colorPrimary))
    ) {
        Text(modifier = Modifier.padding(4.dp), text = text, color = Color.White, style = TextStyle(fontWeight = FontWeight.Bold))
    }
}

@Composable
fun ToppingText() {
    Text(
        modifier = Modifier.padding(vertical = 8.dp),
        text = stringResource(id = R.string.free_topping),
        color = colorResource(id = R.color.colorPrimary),
        style = TextStyle(fontWeight = FontWeight.Bold)
    )
}

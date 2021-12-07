package com.hammer.app.subwaysimulator.ui.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hammer.app.subwaysimulator.R

@Composable
fun EditScreen() {
    var recipeName by remember { mutableStateOf("") }
    Column(modifier = Modifier.background(color = Color.White)) {
        Title(text = stringResource(id = R.string.name_edit))
        OutlinedTextField(
            value = recipeName,
            onValueChange = { recipeName = it },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = colorResource(id = R.color.colorAccent))
        )
        Title(text = stringResource(id = R.string.step1))
        Title(text = stringResource(id = R.string.step2))
        Title(text = stringResource(id = R.string.step3))
        Title(text = stringResource(id = R.string.step4))
        Title(text = stringResource(id = R.string.step5))
        Title(text = stringResource(id = R.string.step5_2))
    }
}

@Composable
private fun Title(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(color = colorResource(id = R.color.colorPrimary))
    ) {
        Text(modifier = Modifier.padding(4.dp), text = text, color = Color.White, style = TextStyle(fontWeight = FontWeight.Bold))
    }
}

@Preview
@Composable
fun PreviewEditScreen() {
    EditScreen()
}

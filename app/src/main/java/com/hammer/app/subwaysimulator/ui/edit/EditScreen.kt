package com.hammer.app.subwaysimulator.ui.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hammer.app.subwaysimulator.R
import com.hammer.app.subwaysimulator.localdata.breads
import com.hammer.app.subwaysimulator.localdata.sandwiches
import com.hammer.app.subwaysimulator.localdata.toppings

@Composable
fun EditScreen() {
    var recipeName by remember { mutableStateOf("") }
    MaterialTheme {
        Column(modifier = Modifier.background(color = Color.White)) {
            Title(text = stringResource(id = R.string.name_edit))
            OutlinedTextField(
                value = recipeName,
                onValueChange = { recipeName = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    cursorColor = colorResource(id = R.color.colorPrimary),
                    focusedBorderColor = colorResource(id = R.color.colorAccent)
                )
            )
            // TODO: TypedArrayで使ってるところを消したらtoListを消す
            // sandwiches
            Title(text = stringResource(id = R.string.step1))
            Spinner(list = sandwiches.toList())
            // breads
            Title(text = stringResource(id = R.string.step2))
            Spinner(list = breads.toList())
            // toppings
            Title(text = stringResource(id = R.string.step3))
            Spinner(list = toppings.toList())
            Title(text = stringResource(id = R.string.step4))
            Title(text = stringResource(id = R.string.step5))
            Title(text = stringResource(id = R.string.step5_2))
        }
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

@Composable
private fun Spinner(list: List<String>) {
    var selectedItemName by remember { mutableStateOf(list.first()) }
    var showMenu by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { showMenu = true },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(modifier = Modifier.padding(4.dp), text = selectedItemName)
        Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = "ArrowDropDown")
    }
    DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }, modifier = Modifier.requiredSizeIn(maxHeight = 400.dp)) {
        list.forEach {
            DropdownMenuItem(onClick = {
                selectedItemName = it
                showMenu = false
            }) {
                Text(text = it)
            }
        }
    }
}

@Preview
@Composable
fun PreviewEditScreen() {
    EditScreen()
}

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hammer.app.subwaysimulator.R
import com.hammer.app.subwaysimulator.localdata.amounts
import com.hammer.app.subwaysimulator.localdata.amountsDressing
import com.hammer.app.subwaysimulator.localdata.breads
import com.hammer.app.subwaysimulator.localdata.dressings
import com.hammer.app.subwaysimulator.localdata.dressingsWoNothing
import com.hammer.app.subwaysimulator.localdata.sandwiches
import com.hammer.app.subwaysimulator.localdata.toppings

@Composable
fun EditScreen() {
    var recipeName by remember { mutableStateOf("") }
    MaterialTheme {
        Column(
            modifier = Modifier
                .background(color = Color.White)
                .verticalScroll(rememberScrollState())
        ) {
            Title(text = stringResource(id = R.string.name_edit))
            TextField(
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
            Spinner(list = sandwiches.toList(), selectedItemAtFirst = sandwiches.first())
            LabelledCheckbox(stringResource(id = R.string.recommend))
            LabelledCheckbox(stringResource(id = R.string.foot_long))
            // breads
            Title(text = stringResource(id = R.string.step2))
            Spinner(list = breads.toList(), selectedItemAtFirst = breads.first())
            LabelledCheckbox(stringResource(id = R.string.toast))
            // toppings
            Title(text = stringResource(id = R.string.step3))
            toppings.forEach {
                LabelledEditText(it)
            }
            // vegetables
            Title(text = stringResource(id = R.string.step4))
            val vegetableTitleList = listOf(
                stringResource(id = R.string.lettuce),
                stringResource(id = R.string.tomato),
                stringResource(id = R.string.green_pepper),
                stringResource(id = R.string.red_onion),
                stringResource(id = R.string.carrot)
            )
            vegetableTitleList.forEach { title ->
                SpinnerWithSmallTitle(list = amounts.toList(), selectedItemAtFirst = amounts[2], title = title)
            }
            Text(
                modifier = Modifier.padding(8.dp),
                text = stringResource(id = R.string.free_topping),
                color = colorResource(id = R.color.colorPrimary),
                style = TextStyle(fontWeight = FontWeight.Bold)
            )
            val toppingTitleList = listOf(
                stringResource(id = R.string.olive),
                stringResource(id = R.string.pickles),
                stringResource(id = R.string.hot_pepper)
            )

            toppingTitleList.forEach { title ->
                SpinnerWithSmallTitle(list = amounts.toList(), selectedItemAtFirst = amounts.first(), title = title)
            }
            // dressings
            Title(text = stringResource(id = R.string.step5))
            SpinnerWithSmallTitle(list = dressings.toList(), selectedItemAtFirst = dressings.first(), title = stringResource(id = R.string.type))
            SpinnerWithSmallTitle(list = amountsDressing.toList(), selectedItemAtFirst = amountsDressing[1], title = stringResource(id = R.string.amount))
            Title(text = stringResource(id = R.string.step5_2))
            SpinnerWithSmallTitle(list = dressingsWoNothing.toList(), selectedItemAtFirst = dressingsWoNothing.first(), title = stringResource(id = R.string.type))
            SpinnerWithSmallTitle(list = amountsDressing.toList(), selectedItemAtFirst = amountsDressing[1], title = stringResource(id = R.string.amount))
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
private fun Spinner(list: List<String>, selectedItemAtFirst: String) {
    var selectedItemName by remember { mutableStateOf(selectedItemAtFirst) }
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

@Composable
private fun LabelledCheckbox(label: String) {
    var isChecked by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .clickable { isChecked = !isChecked },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = { isChecked = it },
            enabled = true,
            colors = CheckboxDefaults.colors(colorResource(id = R.color.colorAccent))
        )
        Text(text = label)
    }
}

@Composable
private fun LabelledEditText(label: String) {
    var input by remember { mutableStateOf(0) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextField(
            value = input.toString(),
            onValueChange = { input = if (it.isEmpty()) 0 else extractOneLengthNumber(it).toInt() },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = colorResource(id = R.color.colorPrimary),
                focusedBorderColor = colorResource(id = R.color.colorAccent)
            ),
            modifier = Modifier.width(45.dp)
        )
        Text(text = " x $label", modifier = Modifier.padding(start = 4.dp))
    }
}

@Composable
private fun SpinnerWithSmallTitle(list: List<String>, selectedItemAtFirst: String, title: String) {
    Column {
        Text(text = title, modifier = Modifier.padding(8.dp), color = Color.DarkGray)
        Spinner(list = list, selectedItemAtFirst = selectedItemAtFirst)
    }
}

private fun extractOneLengthNumber(input: String): String {
    return when (input.length) {
        1 -> input.substring(0, 1)
        2 -> if (input.substring(1, 2) == "0") input.substring(0, 1) else input.substring(1, 2)
        else -> input
    }
}

@Preview
@Composable
fun PreviewEditScreen() {
    EditScreen()
}

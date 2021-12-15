package com.hammer.app.subwaysimulator.ui.edit

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hammer.app.subwaysimulator.R
import com.hammer.app.subwaysimulator.localdata.amounts
import com.hammer.app.subwaysimulator.localdata.amountsDressing
import com.hammer.app.subwaysimulator.localdata.breads
import com.hammer.app.subwaysimulator.localdata.dressings
import com.hammer.app.subwaysimulator.localdata.dressingsWoNothing
import com.hammer.app.subwaysimulator.localdata.sandwiches
import com.hammer.app.subwaysimulator.localdata.toppings
import com.hammer.app.subwaysimulator.ui.common.TextInColoredBox
import com.hammer.app.subwaysimulator.ui.common.ToppingText

@Composable
fun EditScreen() {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    var recipeName by remember { mutableStateOf("") }

    Column(modifier = Modifier.background(color = Color.White)) {
        Column(
            modifier = Modifier
                .weight(1F)
                .padding(horizontal = 8.dp)
                .verticalScroll(rememberScrollState())
        ) {
            TextInColoredBox(text = stringResource(id = R.string.name_edit))
            RecipeNameTextField(focusRequester = focusRequester, focusManager = focusManager, recipeName = recipeName, onNameChange = { recipeName = it })

            // TODO: TypedArrayで使ってるところを消したらtoListを消す
            // sandwiches
            TextInColoredBox(text = stringResource(id = R.string.step1))
            Spinner(list = sandwiches.toList(), selectedItemAtFirst = sandwiches.first())
            LabelledCheckbox(stringResource(id = R.string.recommend))
            LabelledCheckbox(stringResource(id = R.string.foot_long))
            // breads
            TextInColoredBox(text = stringResource(id = R.string.step2))
            Spinner(list = breads.toList(), selectedItemAtFirst = breads.first())
            LabelledCheckbox(stringResource(id = R.string.toast))
            // toppings
            TextInColoredBox(text = stringResource(id = R.string.step3))
            toppings.forEach {
                LabelledEditText(it, focusManager = focusManager)
            }
            // vegetables
            TextInColoredBox(text = stringResource(id = R.string.step4))
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
            ToppingText()
            val toppingTitleList = listOf(
                stringResource(id = R.string.olive),
                stringResource(id = R.string.pickles),
                stringResource(id = R.string.hot_pepper)
            )

            toppingTitleList.forEach { title ->
                SpinnerWithSmallTitle(list = amounts.toList(), selectedItemAtFirst = amounts.first(), title = title)
            }
            // dressings
            TextInColoredBox(text = stringResource(id = R.string.step5))
            SpinnerWithSmallTitle(list = dressings.toList(), selectedItemAtFirst = dressings.first(), title = stringResource(id = R.string.type))
            SpinnerWithSmallTitle(list = amountsDressing.toList(), selectedItemAtFirst = amountsDressing[1], title = stringResource(id = R.string.amount))
            TextInColoredBox(text = stringResource(id = R.string.step5_2))
            SpinnerWithSmallTitle(list = dressingsWoNothing.toList(), selectedItemAtFirst = dressingsWoNothing.first(), title = stringResource(id = R.string.type))
            SpinnerWithSmallTitle(list = amountsDressing.toList(), selectedItemAtFirst = amountsDressing[1], title = stringResource(id = R.string.amount))
        }
        BottomContainer()
    }
    DisposableEffect(Unit) {
        focusRequester.requestFocus()
        onDispose { }
    }
}

@Composable
private fun RecipeNameTextField(focusRequester: FocusRequester, focusManager: FocusManager, recipeName: String, onNameChange: (String) -> Unit) {
    val customTextSelectionColors = TextSelectionColors(
        handleColor = colorResource(id = R.color.colorPrimary),
        backgroundColor = colorResource(id = R.color.colorPrimary).copy(alpha = 0.4f)
    )
    CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
        TextField(
            value = recipeName,
            onValueChange = onNameChange,
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .focusRequester(focusRequester),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = colorResource(id = R.color.colorAccent),
                backgroundColor = Color.White,
                cursorColor = colorResource(id = R.color.colorPrimary),
            )
        )
    }
}

@Composable
private fun Spinner(list: List<String>, selectedItemAtFirst: String) {
    var selectedItemName by remember { mutableStateOf(selectedItemAtFirst) }
    var showMenu by remember { mutableStateOf(false) }
    var dropdownOffset by remember { mutableStateOf(Offset.Zero) }
    val interactionSource = remember { MutableInteractionSource() }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .indication(interactionSource, LocalIndication.current)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = { offset ->
                        val press = PressInteraction.Press(offset)
                        interactionSource.emit(press)

                        tryAwaitRelease()

                        interactionSource.emit(PressInteraction.Release(press))
                        dropdownOffset = offset
                        showMenu = true
                    }
                )
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(modifier = Modifier.padding(4.dp), text = selectedItemName)
        Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = "ArrowDropDown")
    }
    DropdownMenu(
        expanded = showMenu,
        onDismissRequest = { showMenu = false },
        modifier = Modifier
            .requiredSizeIn(maxHeight = 400.dp),
        offset = LocalDensity.current.run {
            DpOffset(dropdownOffset.x.toDp(), 0.dp)
        }
    ) {
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
private fun LabelledEditText(label: String, focusManager: FocusManager) {
    var input by remember { mutableStateOf(0) }
    val customTextSelectionColors = TextSelectionColors(
        handleColor = colorResource(id = R.color.colorPrimary),
        backgroundColor = colorResource(id = R.color.colorPrimary).copy(alpha = 0.4f)
    )
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
            TextField(
                value = input.toString(),
                onValueChange = { input = if (it.isEmpty()) 0 else extractOneLengthNumber(it).toInt() },
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    cursorColor = colorResource(id = R.color.colorPrimary),
                    focusedBorderColor = colorResource(id = R.color.colorAccent)
                ),
                modifier = Modifier.width(45.dp)
            )
        }
        Text(text = " x $label", modifier = Modifier.padding(start = 4.dp))
    }
}

@Composable
private fun SpinnerWithSmallTitle(list: List<String>, selectedItemAtFirst: String, title: String) {
    Column {
        Text(text = title, modifier = Modifier.padding(vertical = 8.dp), color = Color.DarkGray)
        Spinner(list = list, selectedItemAtFirst = selectedItemAtFirst)
    }
}

@Composable
private fun BottomContainer() {
    Row(
        modifier = Modifier
            .background(Color.White)
            .border(BorderStroke(1.dp, Color.LightGray))
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "合計xx円",
            modifier = Modifier.padding(vertical = 16.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp,
        )
        TextButton(
            onClick = { },
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = colorResource(id = R.color.colorAccent),
                contentColor = Color.White,
            )
        ) {
            Text(text = stringResource(id = R.string.complete), color = Color.White)
        }
    }
}

private fun extractOneLengthNumber(input: String): String {
    return when (input.length) {
        2 -> if (input.substring(1, 2) == "0") input.substring(0, 1) else input.substring(1, 2)
        else -> input.substring(0, 1)
    }
}

@Preview
@Composable
fun PreviewEditScreen() {
    EditScreen()
}

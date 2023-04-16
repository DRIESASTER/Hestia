package com.ugnet.sel1.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ugnet.sel1.ui.theme.AccentLicht
import com.ugnet.sel1.ui.theme.MainGroen


@Composable
fun InputWithTitle(title:String,modifier: Modifier = Modifier) {
    var textFieldValue by remember { mutableStateOf("") }

    Column(modifier = modifier
        .padding(10.dp)
        .fillMaxWidth()) {
        Text(text = title,style= MaterialTheme.typography.h6)
        OutlinedTextField(
            value = textFieldValue,
            onValueChange = { newValue ->
                textFieldValue = newValue
            },
            label = { Text(title) },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MainGroen,
                unfocusedBorderColor = AccentLicht,
                focusedLabelColor = MainGroen,
                unfocusedLabelColor = AccentLicht,
                cursorColor = MainGroen,
                textColor = Color.Black,
                backgroundColor = AccentLicht
            )
        )
    }
}


@Preview
@Composable
fun InputWithTitlePreview() {
    Column() {
        InputWithTitle(title = "naam")
        InputWithTitle(title = "email")
    }

}
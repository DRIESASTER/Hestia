package com.ugnet.sel1.ui.components


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ugnet.sel1.ui.theme.AccentLicht
import com.ugnet.sel1.ui.theme.MainGroen

@Composable
fun SwitchButton2(
    initialState: Boolean,
    onStateChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val (currentState, setCurrentState) = remember { mutableStateOf(initialState) }

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(MainGroen)
            .clickable {
                setCurrentState(!currentState)
                onStateChanged(!currentState)
            }
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ToggleButton(
            options = listOf("Issues", "Properties"),
            selectedOption = if (currentState) "Properties" else "Issues",
            onOptionSelected = {
                setCurrentState(it == "Properties")
                onStateChanged(it == "Properties")
            },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun ToggleButton(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .background(Color.Transparent)
            .border(
                width = 2.dp,
                color = MainGroen,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(4.dp)
    ) {
        options.forEach { option ->
            val isSelected = option == selectedOption

            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(20.dp))
                    .background(if (isSelected) AccentLicht else Color.Transparent)
                    .clickable { onOptionSelected(option) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = option,
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center,
                    color = if (isSelected) Color.Black else Color.White,
                )
            }
        }
    }
}

@Preview
@Composable
fun SwitchButton2Preview() {
    SwitchButton2(initialState = true, onStateChanged = {})
}
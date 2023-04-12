package com.ugnet.sel1.ui.components


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.AnimationConstants.DefaultDurationMillis
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
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
    Card(modifier= Modifier.clip(RoundedCornerShape(30.dp)),
        elevation = 16.dp) {
        Row(
            modifier = modifier
                .clip(RoundedCornerShape(30.dp))
                .clickable {
                    setCurrentState(!currentState)
                    onStateChanged(!currentState)
                },
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
}

@Composable
fun ToggleButton(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = Modifier
            .height(55.dp)
            .width(180.dp) // set a fixed width for the box
            .clip(RoundedCornerShape(30.dp))
            .background(Color.Transparent)
            .padding(4.dp)
    ) {
        Row(
            modifier = modifier
                .background(Color.Transparent)
                .padding(4.dp)
        ) {
            options.forEach { option ->
                val isSelected = option == selectedOption
                val visible by remember { mutableStateOf(true) }
                val color by animateColorAsState(
                    targetValue = if (isSelected) MainGroen else Color.Transparent,
                    animationSpec = tween(
                        durationMillis = DefaultDurationMillis,
                        delayMillis = 0
                    )

                )
                val textcolor by animateColorAsState(
                    targetValue = if (isSelected) Color.White else Color.Black,
                    animationSpec = tween(
                        durationMillis = DefaultDurationMillis,
                        delayMillis = 0
                    )

                )

                AnimatedVisibility(visible = visible, enter = fadeIn(), exit = fadeOut()
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(20.dp))
                            .background(
                                color = color,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .clickable { onOptionSelected(option) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = option,
                            style = MaterialTheme.typography.body1,
                            textAlign = TextAlign.Center,
                            color = textcolor,
                            softWrap = true
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun SwitchButton2Preview() {
    SwitchButton2(initialState = true, onStateChanged = {})
}
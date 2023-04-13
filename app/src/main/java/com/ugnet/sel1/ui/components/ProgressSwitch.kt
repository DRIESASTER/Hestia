package com.ugnet.sel1.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.AnimationConstants.DefaultDurationMillis
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ugnet.sel1.ui.theme.GreenProgress
import com.ugnet.sel1.ui.theme.OrangeProgress
import com.ugnet.sel1.ui.theme.RedProgress

@Composable
fun ProgressSwitch(
    modifier: Modifier = Modifier,
    option1 : String = "Not Started",
    option2 : String = "In Progress",
    option3 : String = "Finished",
    initialState: String,
    onStateChanged: (String) -> Unit,
) {
    val (currentState, setCurrentState) = remember { mutableStateOf(initialState) }
    Card(modifier= Modifier.clip(RoundedCornerShape(30.dp)),
        elevation = 16.dp) {
        Row(
            modifier = modifier
                .width(250.dp)
                .height(45.dp)
                .clip(RoundedCornerShape(30.dp))
                .clickable {
                    setCurrentState(
                        when (currentState) {
                            option1 -> option2
                            option2 -> option3
                            option3 -> option1
                            else -> option1
                        }
                    )
                    onStateChanged(currentState)
                },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SwitchOption(
                options = listOf(option1,option2,option3),
                selectedOption = currentState,
                onOptionSelected = {
                    setCurrentState(it)
                    onStateChanged(it)
                },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun SwitchOption(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val totalOptions = options.size
    val selectedIndex = options.indexOf(selectedOption)

    Row(
        modifier = modifier
            .width(150.dp)
            .clip(RoundedCornerShape(30.dp))
            .background(Color.Transparent)
            .padding(4.dp)
    ) {
        options.forEachIndexed { index, option ->
            val isSelected = index == selectedIndex
            val isFirst = index == 0
            val isLast = index == totalOptions - 1
            val blockColor = when (index) {
                0 -> RedProgress
                1 -> OrangeProgress
                2 -> GreenProgress
                else -> Color.Transparent
            }
            val backgroundColor by animateColorAsState(

                targetValue = if (isSelected) blockColor else Color.Transparent,
                animationSpec = tween(
                    durationMillis = DefaultDurationMillis,
                    delayMillis = 0
                )
            )

            val textColor by animateColorAsState(
                targetValue = if (isSelected) Color.White else Color.Black,
                animationSpec = tween(
                    durationMillis = DefaultDurationMillis,
                    delayMillis = 0
                )
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(40.dp)
                    .clip(
                        when {
                            isFirst -> RoundedCornerShape(
                                topStart = 30.dp,
                                bottomStart = 30.dp
                            )
                            isLast -> RoundedCornerShape(
                                topEnd = 30.dp,
                                bottomEnd = 30.dp
                            )
                            else -> RoundedCornerShape(0.dp)
                        }
                    )
                    .background(
                        color = backgroundColor,
                        shape = RoundedCornerShape(30.dp)
                    )
                    .clickable {
                        onOptionSelected(option)
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 5.dp),
                    text = option,
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Center,
                    color = textColor,
                    softWrap = true
                )
            }
        }
    }
}

@Preview
@Composable
fun SwitchButtonPreview() {
    ProgressSwitch(
        option1 = "Yet to Start",
        option2 = "In Progress",
        option3 = "Finished",
        initialState = "Yet to Start",
        onStateChanged = {}
    )
}
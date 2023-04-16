package com.ugnet.sel1.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ugnet.sel1.ui.theme.GreenProgress
import com.ugnet.sel1.ui.theme.OrangeProgress
import com.ugnet.sel1.ui.theme.RedProgress

@Composable
fun ProgressionStatus(
    modifier: Modifier = Modifier,
    currentState: String
) {
    Card(
        modifier = Modifier.clip(RoundedCornerShape(30.dp)),
        elevation = 16.dp
    ) {
        Row(
            modifier = modifier
                .width(130.dp)
                .height(40.dp)
                .clip(RoundedCornerShape(30.dp)),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CurrentStatus(currentState = currentState)
        }
    }
}

val statesColor: HashMap<String, Color> = hashMapOf("Not Started" to RedProgress, "In Progress" to OrangeProgress, "Finished" to GreenProgress)

@Composable
fun CurrentStatus(
    currentState: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .width(150.dp)
            .clip(RoundedCornerShape(30.dp))
            .background(Color.Transparent),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val statusColor = statesColor[currentState]!!

        Text(
            modifier = Modifier.padding(2.dp),
            text = "Status:",
            fontSize = 10.sp,
            textAlign = TextAlign.Center
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .height(35.dp)
                .padding(horizontal = 2.dp)
                .background(
                    color = statusColor,
                    shape = RoundedCornerShape(30.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = currentState,
                style = MaterialTheme.typography.body1,
                fontSize = 10.sp,
                textAlign = TextAlign.Center,
                color = Color.White,
                modifier = Modifier.padding(horizontal = 2.dp)
            )
        }
    }
}

@Preview
@Composable
fun ProgressionStatusPreview() {
    ProgressionStatus(currentState = "Not Started")
}
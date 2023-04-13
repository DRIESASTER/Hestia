package com.ugnet.sel1.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ugnet.sel1.ui.theme.AccentLicht
import com.ugnet.sel1.ui.theme.MainGroen

@Composable
fun IssueCard(title: String,
              tennant:String,
              room:String,
              description:IssueKind,
              status: Int, modifier: Modifier = Modifier) {
    Card(modifier = modifier
        .padding(10.dp)
        .background(Color.Transparent)
        .clip(RoundedCornerShape(10.dp))
        .wrapContentWidth(), contentColor = Color.Transparent) {
            Row(modifier = Modifier
                .background(MainGroen)
                .clip(RoundedCornerShape(10.dp))
                .wrapContentWidth()
                .height(60.dp)) {
                Column(modifier = Modifier
                    .padding(5.dp)
                    .width(120.dp)) {
                    //left side
                    Text(text = title, color = AccentLicht, modifier = Modifier.padding(1.dp))
                    Row(modifier = Modifier
                        .clip(RoundedCornerShape(30.dp))
                        .background(AccentLicht)) {
                        Icon(imageVector = Icons.Rounded.Person, contentDescription = "person", tint = Color.Black, modifier = Modifier.padding(2.dp))
                        Text(
                            text = tennant,
                            color = Color.Black,
                            style = MaterialTheme.typography.overline,
                            modifier = Modifier.padding(2.dp)
                        )
                    }
                }
                Column(modifier = Modifier
                    .padding(5.dp)
                    .width(180.dp)
                    .align(Alignment.CenterVertically)) {
                    //right side with progressswitch
                    ProgressSwitch(
                        initialState = getStatus(status),
                        onStateChanged = {},
                    )
                }
            }
        }
}

fun getStatus(status: Int): String {
    return when (status) {
        0 -> "Not Started"
        1 -> "In Progress"
        2 -> "Finished"
        else -> "Not Started"
    }
}

enum class IssueKind(val value: String) {
    ELECTRICITY("Electricity"),
    WATER("Water"),
    GAS("Gas"),
    SOCIAL("Social"),
    Question("Question"),
    OTHER("Other")
}

@Preview
@Composable
fun IssueCardPreview() {
    IssueCard(title = "leaky faucet", tennant = "Ben De Meurichy", room = "room 001", description = IssueKind.GAS, status = 0)
}
package com.ugnet.sel1.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ugnet.sel1.ui.theme.MainGroen

@Composable
fun IssueCard(title: String,
              tennant:String,
              room:String,
              description:IssueKind,
              status: Int, modifier: Modifier = Modifier) {
    Card(modifier = modifier
        .padding(20.dp)
        .background(MainGroen)
        .clip(RoundedCornerShape(10.dp))
        .width(200.dp), contentColor = MainGroen) {

            Row() {
                Column(modifier = Modifier.padding(10.dp).width(100.dp)) {

                }
                Column() {
                    ProgressSwitch(
                        initialState = getStatus(status),
                        onStateChanged = {},
                        modifier = Modifier.width(200.dp)
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
    IssueCard(title = "Title", tennant = "Tennant", room = "Room", description = IssueKind.ELECTRICITY, status = 0)
}
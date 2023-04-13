package com.ugnet.sel1.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun IssueCard(title: String,
              tennant:String,
              room:String,
              description:IssueKind,
              status: Int, modifier: Modifier = Modifier) {
    
}

enum class IssueKind(val value: String) {
    ELECTRICITY("Electricity"),
    WATER("Water"),
    GAS("Gas"),
    SOCIAL("Social"),
    Question("Question"),
    OTHER("Other")
}
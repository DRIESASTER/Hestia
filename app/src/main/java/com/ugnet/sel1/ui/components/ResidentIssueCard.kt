package com.ugnet.sel1.ui.resident

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ugnet.sel1.domain.models.Status
import com.ugnet.sel1.ui.components.ProgressionStatus
import com.ugnet.sel1.ui.components.getStatus
import com.ugnet.sel1.ui.theme.AccentLicht
import com.ugnet.sel1.ui.theme.MainGroen

@Composable
fun ResidentIssueCard(title: String,
                      tenant:String,
                      room:String,
                      description:String,
                      status: Status, modifier: Modifier = Modifier) {
    val currentStatus = remember { mutableStateOf(status) }

    DisposableEffect(currentStatus.value) {
        println("Current status is: ${getStatus(currentStatus.value)}")
        onDispose { }
    }
    Card(modifier = modifier
        .padding(10.dp)
        .background(Color.Transparent)
        .clip(RoundedCornerShape(10.dp))
        .wrapContentWidth(), contentColor = Color.Transparent) {
        //everythingcontainer
        Row(modifier = Modifier
            .background(MainGroen)
            .clip(RoundedCornerShape(10.dp))
            .wrapContentWidth()
            .height(90.dp)) {
            Column(modifier = Modifier
                .width(150.dp)
                .padding(5.dp)
            ) {
                //left side
                //title
                Text(text = title, color = AccentLicht, modifier = Modifier.padding(1.dp))
                //name
                Row(
                    verticalAlignment = Alignment.CenterVertically
                    ,modifier = Modifier
                        .clip(RoundedCornerShape(30.dp))
                        .background(AccentLicht)
                ) {
                    Icon(imageVector = Icons.Rounded.Person, contentDescription = "person", tint = Color.Black, modifier = Modifier
                        .padding(2.dp)
                        .size(10.dp))
                    Text(
                        text = tenant,
                        color = Color.Black,
                        style = MaterialTheme.typography.body1,
                        fontSize = 10.sp,
                        modifier = Modifier.padding(2.dp)
                    )
                }
                //lowest container
                Row(modifier= Modifier
                    .padding(0.dp, 8.dp, 0.dp, 8.dp)
                    .width(150.dp),horizontalArrangement = Arrangement.Start) {
                    //room
                    Row(
                        verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                            .clip(RoundedCornerShape(30.dp))
                            .background(AccentLicht)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.LocationOn,
                            contentDescription = "person",
                            tint = Color.Black,
                            modifier = Modifier
                                .padding(2.dp)
                                .size(10.dp)
                        )
                        Text(
                            text = room,
                            color = Color.Black,
                            style = MaterialTheme.typography.body1,
                            fontSize = 10.sp,
                            modifier = Modifier.padding(2.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                    //info
                    Row (verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                        .clip(RoundedCornerShape(30.dp))
                        .background(AccentLicht)) {
                        Icon(
                            imageVector = Icons.Rounded.Info,
                            contentDescription = "person",
                            tint = Color.Black,
                            modifier = Modifier
                                .padding(2.dp)
                                .size(10.dp)
                        )
                        Text(
                            text = description,
                            color = Color.Black,
                            style = MaterialTheme.typography.body1,
                            fontSize = 10.sp,
                            modifier = Modifier.padding(2.dp)
                        )
                    }
                }
            }
            Column(modifier = Modifier
                .padding(5.dp)
                .width(130.dp)
                .align(Alignment.CenterVertically)) {
                val statusText = when (status) {
                    Status.notStarted -> "Not Started"
                    Status.inProgress -> "In Progress"
                    Status.finished -> "Finished"
                    else -> ""
                }
                ProgressionStatus(currentState = statusText)
            }
        }
    }
}

@Preview
@Composable
fun ResidentIssueCardPreview() {
    ResidentIssueCard(title = "leaky faucet", tenant = "Ben De Meurichy", room = "room 001", description = "gas", status = Status.notStarted)
}
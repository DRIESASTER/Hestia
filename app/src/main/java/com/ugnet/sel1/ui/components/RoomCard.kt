package com.ugnet.sel1.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.PriorityHigh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.domain.repository.Rooms
import com.ugnet.sel1.ui.manager.properties.PropertyDetailVM
import com.ugnet.sel1.ui.theme.AccentLicht
import com.ugnet.sel1.ui.theme.MainGroen

@Composable
fun RoomDetailOverview(rooms: Rooms,viewmodel: PropertyDetailVM) {
    LazyColumn(content = {
        for( room in rooms ){
            item {
                viewmodel.getIssues(room.roomId!!).collectAsState(initial = Response.Loading).value.let{issues ->
                    when (issues){
                        is Response.Success -> {
                            RoomCard(
                                roomName = room.naam!!,
                                tenantname = if (room.huurderLijst.size==1) room.huurderLijst[0] else "shared",
                                issuecount = issues.data.size
                            )
                        }
                        else -> {
                            CircularProgressIndicator()
                        }
                    }
                }

            }
        }
    })

}

@Composable
fun RoomCard(roomName: String, tenantname: String,issuecount:Int) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .background(Color.Transparent)
            .clip(RoundedCornerShape(15.dp))
            .fillMaxWidth(), contentColor = Color.Transparent
    ) {
        //everythingcontainer
        Row(
            modifier = Modifier
                .background(MainGroen)
                .clip(RoundedCornerShape(10.dp))
                .fillMaxWidth()
                .height(120.dp)
        ) {
            Column(
                modifier = Modifier
                    .width(250.dp)
                    .padding(10.dp)
            ) {
                //name
                Row() {
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(text = roomName, color = AccentLicht, modifier = Modifier.padding(1.dp),fontSize = 20.sp)

                }
                //address
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                        .clip(RoundedCornerShape(30.dp))
                        .wrapContentSize()
                        .background(AccentLicht)
                        .padding(horizontal = 5.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Person,
                        contentDescription = "person",
                        tint = Color.Black,
                        modifier = Modifier
                            .padding(4.dp)
                            .size(12.dp)
                    )
                    Text(
                        text = tenantname,
                        color = Color.Black,
                        style = MaterialTheme.typography.body1,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(4.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                //tennants

                //issuecount
                Row(
                    verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                        .clip(RoundedCornerShape(30.dp))
                        .background(AccentLicht)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.PriorityHigh,
                        contentDescription = "person",
                        tint = Color.Red,
                        modifier = Modifier
                            .padding(4.dp)
                            .size(12.dp)
                    )
                    Text(
                        text = "issues: $issuecount",
                        color = Color.Black,
                        style = MaterialTheme.typography.body1,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(4.dp)
                    )
                }

            }
        }
    }
}

@Preview
@Composable
fun RoomCardDetailPreview() {
    RoomCard("Room 1", "Tenant 1", 1)
}
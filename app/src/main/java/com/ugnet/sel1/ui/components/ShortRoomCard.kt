package com.ugnet.sel1.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ugnet.sel1.domain.models.Room
import com.ugnet.sel1.ui.theme.MainGroen


@Composable
fun ShortRoomCard(roomdata: Room, removeClick: (String) -> Unit) {
    Card(
        backgroundColor = MainGroen,
        modifier = Modifier
            .height(56.dp)
            .padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 4.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        elevation = 8.dp,
        border = BorderStroke(1.dp, Color.DarkGray)
    ) {
        Row(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = roomdata.naam!!, style = MaterialTheme.typography.h6, color = Color.White)
                Text(text = roomdata.huurderId!!, style = MaterialTheme.typography.body1, color = Color.White)
            }
            IconButton(onClick = { removeClick(roomdata.roomId!!) },Modifier.background(Color.Transparent)) {
                Icon(
                    imageVector = Icons.Rounded.Delete,
                    contentDescription = "Remove",
                    tint = Color.White,
                    modifier = Modifier.padding(8.dp).background(Color.Transparent)
                )
            }
        }
    }
}

@Preview
@Composable
fun RoomCardPreview() {
    ShortRoomCard(roomdata = Room("Room 1", "Tenant 1","tster")) {}
}

data class RoomData(val roomName:String,val tenantName:String)
package com.ugnet.sel1.ui.manager.addProp

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ugnet.sel1.ui.components.RoomData
import com.ugnet.sel1.ui.components.ShortRoomCard

@Composable
fun RoomOverview(rooms:List<RoomData>,modifier: Modifier = Modifier, onDeleteClicked:(String)->Unit) {
        Surface(modifier = modifier) {
            LazyColumn (horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()){
                itemsIndexed(rooms) { _, room ->
                    ShortRoomCard(
                        roomdata = room,
                        removeClick = { onDeleteClicked(room.roomName) }
                    )
                    Spacer(modifier = Modifier.height(0.dp))
                }
            }
        }
    }

@Preview
@Composable
fun RoomOverviewPreview() {
    RoomOverview(rooms = listOf(RoomData("Room 1", "Tenant 1"), RoomData("Room 2", "Tenant 2")), onDeleteClicked = {})
}
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.ugnet.sel1.domain.models.Room
import com.ugnet.sel1.ui.components.ShortRoomCard

@Composable
fun RoomOverview(rooms:List<Room>, modifier: Modifier = Modifier, onDeleteClicked:(String)->Unit,viewmodel: RoomEditVM = hiltViewModel(),propid:String) {
        Surface(modifier = modifier) {
            LazyColumn (horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()){
                itemsIndexed(rooms) { _, room ->
                    ShortRoomCard(
                        roomdata = room,
                        removeClick = { onDeleteClicked(room.roomId!!)
                        rooms.filter { it.roomId != room.roomId }
                            if(room.huurderLijst[0] in  rooms.map { it.huurderLijst[0] }){
                                viewmodel.deleterenter(propid,room.huurderLijst[0])
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(0.dp))
                }
            }
        }
    }

@Preview
@Composable
fun RoomOverviewPreview() {
    RoomOverview(rooms = listOf(Room("Room 1", "Tenant 1",listOf("tester"))), onDeleteClicked = {},propid = "test")
}
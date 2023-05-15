package com.ugnet.sel1.ui.manager.addProp

import android.util.Log
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
                        removeClick = {
                        rooms.filter { it.roomId != room.roomId }
                            if(room.huurderLijst.isNotEmpty()){
                                for(i in room.huurderLijst){
                                    Log.d("renterDeletionCheck",
                                        rooms.flatMap { it.huurderLijst }.toString()
                                    )
                                    if ((rooms.flatMap { it.huurderLijst }).indexOf(i) == -1) {
                                        Log.d("RoomOverview", "removing: $i, from renterlist ${rooms.flatMap { it.huurderLijst }} }}")
                                        viewmodel.deleterenter(propid, i)
                                    }
                                }

                            }
                            onDeleteClicked(room.roomId!!)
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
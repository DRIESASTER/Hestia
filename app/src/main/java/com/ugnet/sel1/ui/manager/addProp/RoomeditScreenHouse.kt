package com.ugnet.sel1.ui.manager.addProp

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Save
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.navigation.MyDestinations
import com.ugnet.sel1.ui.components.InputWithTitle
import com.ugnet.sel1.ui.components.SimpleTopBar
import com.ugnet.sel1.ui.theme.AccentLicht
import com.ugnet.sel1.ui.theme.MainGroen


@Composable
fun RoomeditScreenHouse(tenantMail: String,propid: String, viewmodel: RoomEditVM = hiltViewModel(), modifier: Modifier = Modifier, openAndPopUp:(String, String)->Unit) {
    var isPopupVisible by remember { mutableStateOf(false) }
    viewmodel.getRentinglist(propid).collectAsState(initial = Response.Loading).value.let{renters->
        when (renters) {
            is Response.Success -> {
                var rentlist: MutableList<String> = renters.data.toMutableList().map { it.email!! }.toMutableList()
                Scaffold(modifier = Modifier.fillMaxWidth(), topBar = { SimpleTopBar(name = "Manage Rooms", openAndPopup = openAndPopUp)},
                    content = { padding ->

                        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(padding)) {
                            Spacer(modifier = Modifier.height(10.dp))
                            Log.d("RoomeditScreen", "propid: $propid")
                            viewmodel.getRoomsForProperty(propid).collectAsState(initial = Response.Loading).value.let{
                                when (it) {
                                    is Response.Success -> {
                                        if (it.data.isEmpty()) {
                                            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                                                Spacer(modifier = Modifier.height(10.dp))
                                                Text(text = "No rooms found")
                                            }
                                        } else {
                                            RoomOverview(
                                                rooms = it.data,
                                                onDeleteClicked = { viewmodel.deleteRoomFromProperty(propid,it) },
                                                viewmodel = viewmodel,
                                                propid = propid)
                                        }
                                    }
                                    else -> {
                                        CircularProgressIndicator()}
                                }
                                DropdownMenu(
                                    onDismissRequest = { isPopupVisible = false },
                                    expanded = isPopupVisible,
                                ) {
                                    AddRoomHousePopup(
                                        propid = propid,
                                        onClose = { isPopupVisible = false },
                                        onAddRoom = { propid,roomname, tenantmail ->
                                            viewmodel.addroom(propid,roomname, tenantmail)
                                            isPopupVisible = false
                                            //TODO: port to popupscreen for errorhandling and compose, work with boolean to check if save is clicked
                                            if( tenantmail !in rentlist && tenantmail != ""){
                                                rentlist.add(tenantmail)
                                                viewmodel.addrenter(propid,tenantmail)
                                            }
                                        })
                                }
                            }
                            Column(horizontalAlignment =Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                                Spacer(modifier = Modifier.height(10.dp))
                                AddRoomButton(onClick = { isPopupVisible = true })
                            }

                        } }, floatingActionButton = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            IconButton(onClick = { openAndPopUp(MyDestinations.MANAGER_HOME_ROUTE,MyDestinations.ROOM_EDIT_ROUTE_HOUSE) },Modifier.background(
                                AccentLicht, RoundedCornerShape(20.dp)
                            )) {
                                Icon(imageVector = Icons.Rounded.Save, contentDescription = "save", tint = MainGroen)
                            }
                        }

                    })
            }
            else -> {
                CircularProgressIndicator()}
        }
    }


}

@Composable
fun AddRoomHousePopup(
    propid:String,tenantMail: String = "",
    onClose: () -> Unit,
    onAddRoom: (propid:String,roomName: String, tenantMail: String) -> Unit
) {
    var roomName by remember { mutableStateOf("") }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),

        ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            InputWithTitle(
                title = "Room Name", initValue = roomName, onValuechanged = { roomName = it }
            )
            Spacer(modifier = Modifier.height(16.dp))

            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = onClose,
                    modifier = Modifier.padding(end = 8.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = MainGroen)
                ) {
                    Text("Cancel", style = MaterialTheme.typography.button.copy(color = Color.White))
                }
                Button(
                    onClick = {
                        onAddRoom(propid,roomName, tenantMail)
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = MainGroen)
                ) {
                    Text("Save", style = MaterialTheme.typography.button.copy(color = Color.White))
                }
            }
        }
    }
}

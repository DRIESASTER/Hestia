package com.ugnet.sel1.ui.manager.addProp

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.ugnet.sel1.ui.components.SimpleTopBar
import com.ugnet.sel1.ui.theme.AccentLicht
import androidx.compose.material.icons.Icons
import androidx.compose.material.*
import androidx.compose.material.icons.rounded.Save
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.ugnet.sel1.ui.components.InputWithTitle
import com.ugnet.sel1.ui.theme.MainGroen


@Composable
fun RoomeditScreen(propid:String,viewmodel: RoomEditVM = hiltViewModel(), modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    var isPopupVisible by remember { mutableStateOf(false) }
    viewmodel.propid = propid

    Scaffold(modifier = Modifier.fillMaxWidth(), topBar = { SimpleTopBar(name = "Manage Rooms", navController = navController)},
        content = { padding ->
            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(padding)) {
                RoomOverview(rooms = viewmodel.processRooms(), onDeleteClicked = {viewmodel.deleteRoomFromProperty(it)} )
                DropdownMenu(
                    onDismissRequest = { isPopupVisible = false },
                    expanded = isPopupVisible,
                ) {
                    AddRoomPopup(
                        onClose = { isPopupVisible = false },
                        onAddRoom = { roomname,tenantmail -> viewmodel.addroom(roomname,tenantmail)
                            isPopupVisible = false})
                }
                AddRoomButton(onClick = { isPopupVisible = true })
            } }, floatingActionButton = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(onClick = { /*nav to the mainscreen of manager*/ },Modifier.background(
                    AccentLicht, RoundedCornerShape(20.dp)
                )) {
                    Icon(imageVector = Icons.Rounded.Save, contentDescription = "save", tint = MainGroen)
                }
            }

        })

    }



@Composable
fun AddRoomButton(modifier:Modifier = Modifier, onClick: () -> Unit = {}){
    FloatingActionButton(onClick = onClick ,
        Modifier
            .background(Color.Transparent)
            .border(
                1.dp,
                Color.DarkGray, RoundedCornerShape(30.dp)
            )) {
        Text(text ="Add Room", color = Color.White, modifier = Modifier.padding(start= 10.dp, end = 10.dp))
    }
}

@Composable
fun AddRoomPopup(
    onClose: () -> Unit,
    onAddRoom: (roomName: String, tenantName: String) -> Unit
) {
    var roomName by remember { mutableStateOf("") }
    var tenantMail by remember { mutableStateOf("") }

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
            InputWithTitle(
                title = "Tenant Email", initValue = tenantMail, onValuechanged = { tenantMail = it }
            )
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
                    onClick = { onAddRoom(roomName, tenantMail) },
                    colors = ButtonDefaults.buttonColors(backgroundColor = MainGroen)
                ) {
                    Text("Save", style = MaterialTheme.typography.button.copy(color = Color.White))
                }
            }
        }
    }
}

@Preview
@Composable
fun AddRoomPopupPreview() {
    AddRoomPopup(onClose = {}, onAddRoom = { _, _ -> })
}


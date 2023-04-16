package com.ugnet.sel1.ui.manager.addProp

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.ugnet.sel1.ui.components.InputWithTitle
import com.ugnet.sel1.ui.components.SimpleTopBar
import com.ugnet.sel1.ui.components.SwitchButton2
import com.ugnet.sel1.ui.theme.MainGroen
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel


@Composable
fun AddPropMainScreen(viewmodel: AddPropVM = hiltViewModel(), modifier: Modifier ) {
    val navController = rememberNavController()
    Scaffold(topBar = { SimpleTopBar(name = "Add Property", navController = navController)},
        content = { padding ->
            SwitchButton2(initialState = viewmodel.isHouse, onStateChanged = {viewmodel.changeState()})
            if (viewmodel.isHouse){
                AddHouse(viewmodel = viewmodel,modifier = Modifier.padding(padding))
            } else {
                AddAppartement(viewmodel = viewmodel,modifier = Modifier.padding(padding))
            }
        })

}

@Composable
fun AddHouse(viewmodel:AddPropVM, modifier:Modifier = Modifier) {
    Column(horizontalAlignment = Alignment.CenterHorizontally){
            InputWithTitle(title = "City", initValue = viewmodel.city, onValuechanged = {viewmodel.city = it})
            InputWithTitle(title = "Street", initValue = viewmodel.street, onValuechanged = {viewmodel.street = it})
            InputWithTitle(title = "Number", initValue = viewmodel.number, onValuechanged = {viewmodel.number = it})
            InputWithTitle(title = "Postal Code", initValue = viewmodel.postalCode, onValuechanged = {viewmodel.postalCode = it})
            InputWithTitle(title = "Tenant", initValue = viewmodel.tenant, onValuechanged = {viewmodel.tenant = it})
    }

}

@Composable
fun AddAppartement(viewmodel:AddPropVM,modifier:Modifier = Modifier) {
    var isPopupVisible = remember { mutableStateOf(false) }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        if (isPopupVisible.value) {
            Popup(
                onDismissRequest = { isPopupVisible.value = false },
                alignment = Alignment.Center
            ) {
            AddRoomPopup(
                onClose = { isPopupVisible.value = false },
                onAddRoom = { roomname,tenantname -> viewmodel.addRoom(roomname,tenantname)
                    isPopupVisible.value = false})
            }
        }
        InputWithTitle(title = "City", initValue = viewmodel.city, onValuechanged = {viewmodel.city = it})
        InputWithTitle(title = "Street", initValue = viewmodel.street, onValuechanged = {viewmodel.street = it})
        InputWithTitle(title = "Number", initValue = viewmodel.number, onValuechanged = {viewmodel.number = it})
        InputWithTitle(title = "Postal Code", initValue = viewmodel.postalCode, onValuechanged = {viewmodel.postalCode = it})
        AddRoomButton(onClick = { isPopupVisible.value = true })
        RoomOverview(rooms = viewmodel.rooms, onDeleteClicked = { roomname -> viewmodel.removeRoom(roomname)})
    }

}


@Composable
fun AddRoomButton(modifier:Modifier = Modifier, onClick: () -> Unit = {}){
    FloatingActionButton(onClick = { onClick },
        Modifier
            .background(MainGroen)
            .border(
                1.dp,
                Color.DarkGray
            )) {
        Text(text ="Add Room", color = Color.White, modifier = Modifier.padding(start= 10.dp, end = 10.dp))
    }
}

@Composable
fun AddRoomPopup(
    onClose: () -> Unit,
    onAddRoom: (roomName: String, tenantName: String) -> Unit
) {
    var roomName = remember { mutableStateOf("") }
    var tenantName = remember { mutableStateOf("") }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = 8.dp
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            InputWithTitle(
                title = "Room Name", initValue = roomName.value, onValuechanged = { roomName.value = it }
            )
            Spacer(modifier = Modifier.height(16.dp))
            InputWithTitle(
                title = "Tenant Name", initValue = tenantName.value, onValuechanged = { tenantName.value = it }
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
                    Text("Cancel")
                }
                Button(
                    onClick = { onAddRoom(roomName.value, tenantName.value) }
                ) {
                    Text("Save")
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

@Preview
@Composable
fun AddpropMainScreenPreview() {
    AddPropMainScreen( modifier = Modifier)
}


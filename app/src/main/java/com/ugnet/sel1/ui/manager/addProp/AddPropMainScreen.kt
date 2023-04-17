package com.ugnet.sel1.ui.manager.addProp

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Save
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.ui.components.InputWithTitle
import com.ugnet.sel1.ui.components.SimpleTopBar
import com.ugnet.sel1.ui.components.SwitchButton2
import com.ugnet.sel1.ui.theme.AccentLicht
import com.ugnet.sel1.ui.theme.MainGroen


@Composable
fun AddPropMainScreen(viewmodel: AddPropVM = hiltViewModel(), modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    Scaffold(modifier = Modifier.fillMaxWidth(), topBar = { SimpleTopBar(name = "Add Property", navController = navController)},
        content = { padding ->
            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            SwitchButton2(
                option1 = "House",
                option2 = "Appartment",
                initialState = viewmodel.isHouse,
                onStateChanged = { viewmodel.changeState() })
            if (viewmodel.isHouse) {
                AddHouse(viewmodel = viewmodel, modifier = Modifier.padding(padding))
            } else {
                AddAppartement(viewmodel = viewmodel, modifier = Modifier.padding(padding))
            }

        }
        }, floatingActionButton = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(onClick = { trySave(viewmodel,navController) },Modifier.background(
                    AccentLicht, RoundedCornerShape(20.dp))) {
                    Icon(imageVector = Icons.Rounded.Save, contentDescription = "save", tint = MainGroen)
                }
            }

        })
}

//TODO: fix input validation
fun trySave(viewmodel: AddPropVM,navigator:NavController) {
    when(val userresponse = viewmodel.userResponse){
        is Response.Success-> {
            viewmodel.saveProp(userresponse.data?.uid.toString())
            when(val propresponse = viewmodel.addPropertyResponse){
                is Response.Success ->{
                    /**route to main screen*/
                    navigator.popBackStack()
                }
                else -> {}
            }
        }
        else -> {}
    }
}

@Composable
fun AddHouse(viewmodel:AddPropVM, modifier:Modifier = Modifier) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()){
            InputWithTitle(title = "City", initValue = viewmodel.city, onValuechanged = {viewmodel.city = it})
            InputWithTitle(title = "Street", initValue = viewmodel.street, onValuechanged = {viewmodel.street = it})
            InputWithTitle(title = "Number", initValue = viewmodel.number, onValuechanged = {viewmodel.number = it})
            InputWithTitle(title = "Postal Code", initValue = viewmodel.postalCode, onValuechanged = {viewmodel.postalCode = it})
            InputWithTitle(title = "Tenant", initValue = viewmodel.tenant, onValuechanged = {viewmodel.tenant = it})
    }

}

@Composable
fun AddAppartement(viewmodel:AddPropVM,modifier:Modifier = Modifier) {
    var isPopupVisible by remember { mutableStateOf(false) }
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.TopStart)){

        DropdownMenu(
            onDismissRequest = { isPopupVisible = false },
            expanded = isPopupVisible,
        ) {
            AddRoomPopup(
                onClose = { isPopupVisible = false },
                onAddRoom = { roomname,tenantname -> viewmodel.addRoom(roomname,tenantname)
                    isPopupVisible = false})
            }
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            InputWithTitle(title = "City", initValue = viewmodel.city, onValuechanged = {viewmodel.city = it})
            InputWithTitle(title = "Street", initValue = viewmodel.street, onValuechanged = {viewmodel.street = it})
            InputWithTitle(title = "Number", initValue = viewmodel.number, onValuechanged = {viewmodel.number = it})
            InputWithTitle(title = "Postal Code", initValue = viewmodel.postalCode, onValuechanged = {viewmodel.postalCode = it})
            AddRoomButton(onClick = { isPopupVisible = true })
            Spacer(modifier = Modifier.height(10.dp))
            RoomOverview(rooms = viewmodel.rooms, onDeleteClicked = { roomname -> viewmodel.removeRoom(roomname)})
        }

    }

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
    var tenantName by remember { mutableStateOf("") }

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
                title = "Tenant Name", initValue = tenantName, onValuechanged = { tenantName = it }
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
                    onClick = { onAddRoom(roomName, tenantName) },
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

//@Preview
//@Composable
//fun AddpropMainScreenPreview() {
//    AddPropMainScreen(viewmodel = FakeAddPropVM(), modifier = Modifier)
//}
//
//class FakeAddPropVM : AddPropVM() {
//    override var isHouse: Boolean by mutableStateOf(false)
//    override var city by mutableStateOf( "city")
//    override var street: String = "street"
//    override var number: String = "number"
//    override var postalCode: String = "postalCode"
//    override var tenant: String = "tenant"
//    override fun changeState() {
//        isHouse = !isHouse
//    }
//
//}

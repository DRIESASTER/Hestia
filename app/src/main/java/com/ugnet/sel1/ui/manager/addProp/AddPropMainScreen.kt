package com.ugnet.sel1.ui.manager.addProp

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.ugnet.sel1.ui.components.InputWithTitle
import com.ugnet.sel1.ui.components.SimpleTopBar
import com.ugnet.sel1.ui.components.SwitchButton2
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier



@Composable
fun AddPropMainScreen(viewmodel: AddPropVM, modifier: Modifier ) {
    val navController = rememberNavController()
    Scaffold(topBar = { SimpleTopBar(name = "Add Property", navController = navController)},
        content = { padding ->
           if (viewmodel.isHouse){
               AddHouse(viewmodel = viewmodel,modifier = Modifier.padding(padding))
           } else {
                AddAppartement(viewmodel = viewmodel,modifier = Modifier.padding(padding))
           }
        })
    SwitchButton2(initialState = viewmodel.isHouse, onStateChanged = {viewmodel.changeState()})
}

@Composable
fun AddHouse(viewmodel:AddPropVM, modifier:Modifier = Modifier) {
    InputWithTitle(title = "City")
    InputWithTitle(title = "Street")
    InputWithTitle(title = "Number")
    InputWithTitle(title = "Postal Code")
    InputWithTitle(title = "Tenant")
}

@Composable
fun AddAppartement(viewmodel:AddPropVM,modifier:Modifier = Modifier) {
    InputWithTitle(title = "City")
    InputWithTitle(title = "Street")
    InputWithTitle(title = "Number")
    InputWithTitle(title = "Postal Code")
    RoomOverview(rooms = viewmodel.rooms, onDeleteClicked = { roomname -> viewmodel.removeRoom(roomname)})
}
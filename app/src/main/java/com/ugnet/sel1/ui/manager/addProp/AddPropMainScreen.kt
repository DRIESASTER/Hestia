package com.ugnet.sel1.ui.manager.addProp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowRight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.navigation.MyDestinations
import com.ugnet.sel1.ui.components.InputWithTitle
import com.ugnet.sel1.ui.components.SimpleTopBar
import com.ugnet.sel1.ui.components.SwitchButton2
import com.ugnet.sel1.ui.theme.AccentLicht
import com.ugnet.sel1.ui.theme.MainGroen


@Composable
fun AddPropMainScreen(viewmodel: AddPropVM = hiltViewModel(), modifier: Modifier = Modifier,openAndPopUp: (String, String) -> Unit) {
    Scaffold(modifier = Modifier.fillMaxWidth(), topBar = { SimpleTopBar(name = "Add Property", openAndPopup = openAndPopUp)},
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

            }

        })
}

//TODO: fix input validation
@Composable
fun trySave(viewmodel: AddPropVM,openAndPopUp: (String, String) -> Unit) {
    when (val userresponse = viewmodel.userResponse) {
        is Response.Success -> {
            IconButton(onClick = { viewmodel.saveProp(userresponse.data?.uid.toString()) },
                Modifier
                    .background(
                        AccentLicht, RoundedCornerShape(20.dp)
                    )
                    .size(20.dp)) {
                Icon(imageVector = Icons.Rounded.ArrowRight, contentDescription = "next", tint = MainGroen)
            }
            when(val propertyresponse = viewmodel.addPropertyResponse){
                is Response.Success -> {
                    //add propid to appstate//
                    openAndPopUp(MyDestinations.ROOM_EDIT_ROUTE,MyDestinations.ADD_PROPERTY)
                }
                else -> {
                    CircularProgressIndicator()}
            }
            //TODO: navigate to roomeditscreen and pass the propid


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
            InputWithTitle(title = "Tenant email", initValue = viewmodel.tenant, onValuechanged = {viewmodel.tenant = it})
    }

}

@Composable
fun AddAppartement(viewmodel:AddPropVM,modifier:Modifier = Modifier) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.TopStart)){


        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            InputWithTitle(title = "City", initValue = viewmodel.city, onValuechanged = {viewmodel.city = it})
            InputWithTitle(title = "Street", initValue = viewmodel.street, onValuechanged = {viewmodel.street = it})
            InputWithTitle(title = "Number", initValue = viewmodel.number, onValuechanged = {viewmodel.number = it})
            InputWithTitle(title = "Postal Code", initValue = viewmodel.postalCode, onValuechanged = {viewmodel.postalCode = it})
            Spacer(modifier = Modifier.height(10.dp))
        }

    }

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

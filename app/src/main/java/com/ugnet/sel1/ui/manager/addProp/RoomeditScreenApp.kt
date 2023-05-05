package com.ugnet.sel1.ui.manager.addProp

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Save
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.navigation.MyDestinations
import com.ugnet.sel1.ui.components.InputWithTitle
import com.ugnet.sel1.ui.components.ShortUserCard
import com.ugnet.sel1.ui.components.SimpleTopBar
import com.ugnet.sel1.ui.theme.AccentLicht
import com.ugnet.sel1.ui.theme.MainGroen

//TODO: make users of room a list, fix

@Composable
fun RoomeditScreenApp(viewmodel: RoomEditVM = hiltViewModel(), modifier: Modifier = Modifier, navigate:(String)->Unit) {
    var propid = viewmodel.propid
    var isPopupVisible by remember { mutableStateOf(false) }
    viewmodel.getRentinglist(propid).collectAsState(initial = Response.Loading).value.let{renters->
        when (renters) {
            is Response.Success -> {
                var rentlist: MutableList<String> = renters.data.toMutableList().map { it.email!! }.toMutableList()
                Scaffold(modifier = Modifier.fillMaxWidth(), topBar = { SimpleTopBar(name = "Manage Rooms", navigate = navigate)},
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
                                        CircularProgressIndicator(color = MainGroen)}
                                }
                                DropdownMenu(
                                    onDismissRequest = { isPopupVisible = false },
                                    expanded = isPopupVisible,
                                ) {
                                    AddRoomPopup(
                                        propid = propid,
                                        onClose = { isPopupVisible = false },
                                        onAddRoom = { propid,roomname, tenantmails ->
                                            viewmodel.addroom(propid,roomname,tenantmails)
                                            isPopupVisible = false
                                            //TODO: port to popupscreen for errorhandling and compose, work with boolean to check if save is clicked
                                            for (tenantmail in tenantmails){
                                                if( tenantmail !in rentlist && tenantmail != ""){
                                                    rentlist.add(tenantmail)
                                                    viewmodel.addrenter(propid,tenantmail)
                                                }
                                            }
                                        },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .align(Alignment.CenterHorizontally)
                                    )

                                }
                            }
                            Column(horizontalAlignment =Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                                Spacer(modifier = Modifier.height(10.dp))
                                AddRoomButton(onClick = { isPopupVisible = true })
                            }

                            } }, floatingActionButton = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            IconButton(onClick = { navigate(MyDestinations.MANAGER_HOME_ROUTE) },Modifier.background(
                                AccentLicht, RoundedCornerShape(20.dp)
                            )) {
                                Icon(imageVector = Icons.Rounded.Save, contentDescription = "save", tint = MainGroen)
                            }
                        }

                    })
            }
            else -> {
                CircularProgressIndicator(color = MainGroen)}
        }
    }


    }



@Composable
fun AddRoomButton(modifier:Modifier = Modifier, onClick: () -> Unit = {}){
    FloatingActionButton(onClick = onClick , backgroundColor = MainGroen, modifier =
    Modifier
        .border(
            1.dp,
            Color.DarkGray, RoundedCornerShape(40.dp)
        )
        .wrapContentSize()) {
        Text(text ="Add Room" ,color = Color.White, modifier = Modifier.padding(start= 10.dp, end = 10.dp))
    }
}

@Composable
fun AddRoomPopup(
    propid:String,
    onClose: () -> Unit,
    onAddRoom: (propid:String,roomName: String, tenantMails: MutableList<String>) -> Unit,
    modifier: Modifier = Modifier
) {
    var roomName by remember { mutableStateOf("") }
    var tenantMail by remember { mutableStateOf("") }
    var shared by remember { mutableStateOf(false) }
    var tenantMails = remember { mutableStateListOf<String>() }


        Column(modifier = modifier.fillMaxWidth().fillMaxHeight()) {

        Column(
            modifier = Modifier
                .padding(16.dp)
                .height(350.dp),
        ) {
            InputWithTitle(
                title = "Room Name", initValue = roomName, onValuechanged = { roomName = it }
            )
            Spacer(modifier = Modifier.height(10.dp))
            if (!shared){
                Row {
                    InputWithTitle(
                        title = "Tenant Email", initValue = tenantMail, onValuechanged = { tenantMail = it }, modifier = Modifier.width(200.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    IconButton(onClick = {
                        Log.d("AddRoomPopup", "added tenantmail: $tenantMail")
                        if (tenantMail != "") {
                            tenantMails.add(tenantMail)
                            tenantMail = ""
                        } },modifier = Modifier.padding(top = 40.dp, bottom = 30.dp) ){
                        Icon(imageVector = Icons.Rounded.Add, contentDescription = "add")
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "Tenants:")
                Row(modifier= Modifier.fillMaxWidth().height(160.dp),horizontalArrangement = Arrangement.Center){

                LazyColumn(modifier = Modifier
                    .width(300.dp)
                    .height(160.dp)){
                    itemsIndexed(items = tenantMails){index, item ->
                        ShortUserCard(name = item, removeClick = {tenantMails.remove(item)})
                    }
                }
                }
                }
            }
        Column (modifier = Modifier.fillMaxWidth().padding(10.dp)){
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Shared Room?")
            Switch(checked =shared , onCheckedChange = {shared=!shared})

            Spacer(modifier = Modifier.height(10.dp))
            Row(verticalAlignment = Alignment.Bottom,
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
                        return@Button if(shared) {
                            onAddRoom(propid,roomName, mutableListOf<String>())
                        } else{
                            onAddRoom(propid,roomName, tenantMails)
                        }
                    },
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
    AddRoomPopup(propid = "",onClose = {}, onAddRoom = {_, _, _ -> })
}


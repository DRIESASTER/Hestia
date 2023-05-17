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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.navigation.MyDestinations
import com.ugnet.sel1.ui.components.InputWithTitle
import com.ugnet.sel1.ui.components.SimpleTopBar
import com.ugnet.sel1.ui.theme.AccentLicht
import com.ugnet.sel1.ui.theme.MainGroen
import kotlinx.coroutines.delay


@Composable
fun RoomeditScreenHouse(viewModel: RoomEditVM = hiltViewModel(), modifier: Modifier = Modifier, navigate : (String) -> Unit) {
    var isPopupVisible by remember { mutableStateOf(false) }

    val propid = viewModel.propid
    viewModel.addrenter(propid, viewModel.tenantmail)


    viewModel.getRentinglist(propid).collectAsState(initial = Response.Loading).value.let{renters->
        when (renters) {
            is Response.Success -> {
                var rentlist: MutableList<String> = renters.data.toMutableList().map { it.email!! }.toMutableList()
                Scaffold(modifier = Modifier.fillMaxWidth(), topBar = { SimpleTopBar(name = "Manage Rooms", navigate = navigate)},
                    content = { padding ->

                        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(padding)) {
                            Spacer(modifier = Modifier.height(10.dp))
                            Log.d("RoomeditScreen", "propid: $propid")
                            viewModel.getRoomsForProperty(propid).collectAsState(initial = Response.Loading).value.let{
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
                                                onDeleteClicked = { viewModel.deleteRoomFromProperty(propid,it) },
                                                viewmodel = viewModel,
                                                propid = propid)
                                        }
                                    }
                                    else -> {
                                        CircularProgressIndicator(color = MainGroen)}
                                }
                                if (isPopupVisible){
                                AlertDialog(modifier = modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(0.8f), onDismissRequest = {isPopupVisible=false},text= {
                                    AddRoomHousePopup(
                                        propid = propid,
                                        onClose = { isPopupVisible = false },
                                        onAddRoom = { propid,roomname ->
                                            viewModel.addroom(propid, roomname, mutableListOf())
                                            isPopupVisible = false
                                            //TODO: port to popupscreen for errorhandling and compose, work with boolean to check if save is clicked

                                        })
                                }, buttons = {})}
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddRoomHousePopup(
    propid:String,tenantMail: String = "",
    onClose: () -> Unit,
    onAddRoom: (propid:String,roomName: String) -> Unit
) {
    var roomName by remember { mutableStateOf("") }
    val showKeyboard = remember { mutableStateOf(true) }
    val focusRequester = remember { FocusRequester() }
    val keyboard = LocalSoftwareKeyboardController.current

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
                title = "Room Name", initValue = roomName, onValuechanged = { roomName = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
            )
            // LaunchedEffect prevents endless focus request
            LaunchedEffect(focusRequester) {
                if (showKeyboard.value) {
                    delay(100) // Make sure you have delay here
                    focusRequester.requestFocus()

                    keyboard?.show()
                }}
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
                        onAddRoom(propid,roomName)
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = MainGroen)
                ) {
                    Text("Save", style = MaterialTheme.typography.button.copy(color = Color.White))
                }
            }
        }
    }
}

package com.ugnet.sel1.ui.resident

import android.util.Log
import com.ugnet.sel1.R.string as AppText
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ugnet.sel1.common.snackbar.SnackbarManager
import com.ugnet.sel1.domain.models.IssueType
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.navigation.MyDestinations
import com.ugnet.sel1.ui.components.InputWithTitle
import com.ugnet.sel1.ui.theme.AccentLicht
import com.ugnet.sel1.ui.theme.MainGroen

val displayIssueType: HashMap<IssueType, String> = hashMapOf(IssueType.water to "Water", IssueType.electricity to "Electricity", IssueType.gas to "Gas", IssueType.other to "other")

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddIssueScreen(
    viewModel: ResidentHomeVM,
    navigate : (String) -> Unit
) {
    val txtFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        focusedBorderColor = MainGroen,
        unfocusedBorderColor = AccentLicht,
        focusedLabelColor = MainGroen,
        unfocusedLabelColor = AccentLicht,
        cursorColor = MainGroen,
        textColor = Color.Black,
        backgroundColor = AccentLicht
    )

    var propertyExpanded by remember { mutableStateOf(false) }
    var roomExpanded by remember { mutableStateOf(false) }
    var typeExpanded by remember { mutableStateOf(false) }

    var issueTitle by remember { mutableStateOf("") }
    var issueDescription by remember { mutableStateOf("") }

    var issuePropertyAddress by remember { mutableStateOf("Select Property") }
    var issuePropertyId by remember { mutableStateOf("") }

    var issueRoomName by remember { mutableStateOf("First select a property to select a room...") }
    var issueRoomId by remember { mutableStateOf("") }
    var canSelectRoom by remember { mutableStateOf(false) }

    var issueType by remember { mutableStateOf(IssueType.water) }

    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Add new issue",  fontWeight = FontWeight.Bold, fontSize = 30.sp, color = MainGroen)

        InputWithTitle(title = "Title", initValue = issueTitle, onValuechanged = {issueTitle = it})

        InputWithTitle(title = "Description", initValue = issueDescription, onValuechanged = {issueDescription = it})

        //Property dropdown menu
        Text(text = "Select property",style= MaterialTheme.typography.h6)
        ExposedDropdownMenuBox(
            expanded = propertyExpanded,
            onExpandedChange = { propertyExpanded = it }
        ) {
            OutlinedTextField(
                value = issuePropertyAddress,
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = propertyExpanded)
                },
                colors = txtFieldColors
            )
            ExposedDropdownMenu(expanded = propertyExpanded, onDismissRequest = { propertyExpanded = false }) {
                when(val response = viewModel.allRentedPropertiesResponse) {
                    is Response.Success -> {
                        for (residence in response.data) {
                            val resAddress = residence.straat!!+" "+residence.huisnummer!!+", "+residence.postcode!!+" "+residence.stad!!
                            DropdownMenuItem(onClick = {
                                issuePropertyId = residence.propertyId!!
                                issuePropertyAddress = resAddress
                                propertyExpanded = false
                                canSelectRoom = true
                            }) {
                                Text(text = resAddress)
                            }
                        }
                    }
                    else -> {
                        CircularProgressIndicator(backgroundColor = MainGroen,color = AccentLicht)
                    }
                }
            }
        }

        //Room dropdown menu
        Text(text = "Select room",style= MaterialTheme.typography.h6)
        ExposedDropdownMenuBox(
            expanded = roomExpanded,
            onExpandedChange = {
                if (canSelectRoom) {
                    roomExpanded = it
                }
            }
        ) {
            OutlinedTextField(
                value = issueRoomName,
                onValueChange = {},
                readOnly = true,
                enabled = canSelectRoom,
                trailingIcon = {
                    if (canSelectRoom) {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = roomExpanded)
                    }
                },
                colors = txtFieldColors
            )
            ExposedDropdownMenu(expanded = roomExpanded, onDismissRequest = { roomExpanded = false }) {
                viewModel.getAccesibleRoomsForProperty(issuePropertyId).collectAsState(initial = Response.Loading).value.let { rooms ->
                    when(rooms) {
                        is Response.Success -> {
                            if (rooms.data.isEmpty()) {
                                Log.d("addIssueScreen","No rooms for this property") //FIXME: properly display issue
                            } else {
                                for (room in rooms.data) {
                                    DropdownMenuItem(onClick = {
                                        issueRoomId = room.roomId!!
                                        issueRoomName = room.naam!!
                                        roomExpanded = false
                                    }) {
                                        Text(text = room.naam!!)
                                    }
                                }
                            }
                        }
                        else -> {
                            CircularProgressIndicator(backgroundColor = MainGroen,color = AccentLicht)
                        }
                    }
                }
            }
        }

        Text(text = "Select the type of issue",style= MaterialTheme.typography.h6)
        ExposedDropdownMenuBox(
            expanded = typeExpanded,
            onExpandedChange = { typeExpanded = it }
        ) {
            OutlinedTextField(
                value = displayIssueType[issueType]!!,
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = typeExpanded)
                },
                colors = txtFieldColors
            )
            ExposedDropdownMenu(expanded = typeExpanded, onDismissRequest = { typeExpanded = false }) {
                for((key, value) in displayIssueType) {
                    DropdownMenuItem(onClick = {
                        issueType = key
                        typeExpanded = false
                    }) {
                        Text(text = displayIssueType[key]!!)
                    }
                }
            }
        }
        Row(modifier = Modifier.padding(20.dp)) {
            Button(
                onClick = { navigate(MyDestinations.HIREE_HOME_ROUTE) },
                modifier = Modifier.padding(end = 8.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = MainGroen)
            ) {
                Text("Cancel", style = MaterialTheme.typography.button.copy(color = Color.White))
            }
            Button(
                onClick = {
                    if(issuePropertyId == "" || issueRoomId == "" || issueTitle == "" || issueDescription == "") {
                        SnackbarManager.showMessage(AppText.add_issue_error)
                    } else {
                        viewModel.addIssue(issueDescription, issueTitle, issuePropertyId, issueRoomId, issueType)
                        navigate(MyDestinations.HIREE_HOME_ROUTE)
                    }
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = MainGroen)
            ) {
                Text("AddIssue", style = MaterialTheme.typography.button.copy(color = Color.White))
            }
        }
    }
}
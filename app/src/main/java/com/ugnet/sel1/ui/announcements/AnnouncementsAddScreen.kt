package com.ugnet.sel1.ui.announcements

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ugnet.sel1.domain.models.Property
import com.ugnet.sel1.ui.theme.AccentLicht
import com.ugnet.sel1.ui.theme.MainGroen


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AnnouncementsAddScreen(
    viewModel: AnnouncementsViewModel = hiltViewModel(),
    onSave: () -> Unit
) {
    var text by remember { mutableStateOf("") }
    var selectedProperty by remember { mutableStateOf(Property()) }

    var propertyExpanded by remember { mutableStateOf(false) }
    var issuePropertyAddress by remember { mutableStateOf("Select Property") }

    val txtFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        focusedBorderColor = MainGroen,
        unfocusedBorderColor = AccentLicht,
        focusedLabelColor = MainGroen,
        unfocusedLabelColor = AccentLicht,
        cursorColor = MainGroen,
        textColor = Color.Black,
        backgroundColor = AccentLicht
    )

    Column(modifier = Modifier.fillMaxWidth().wrapContentHeight(), horizontalAlignment = Alignment.Start) {
        Text(text = "Announcement", fontWeight = FontWeight.Bold, modifier = Modifier.padding(10.dp))
        OutlinedTextField(value = text, onValueChange = {content:String->text=content},
        colors = txtFieldColors, modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f)
                .padding(vertical = 10.dp))

        Text(text = "Property", fontWeight = FontWeight.Bold, modifier = Modifier.padding(10.dp))
        Column(modifier = Modifier.fillMaxWidth().wrapContentHeight(), horizontalAlignment = Alignment.CenterHorizontally){
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
                when (val uiState = viewModel.uiState.collectAsState().value) {
                    AnnouncementUiState.Loading -> {
                        CircularProgressIndicator(backgroundColor = MainGroen,color = AccentLicht)
                    }
                    is AnnouncementUiState.Success -> {
                        val properties = uiState.ownedProperties
                        if (properties.isNotEmpty() && issuePropertyAddress == "Select Property") {
                            issuePropertyAddress = properties[0].straat!! + " " + properties[0].huisnummer!! + ", " + properties[0].postcode!! + " " + properties[0].stad!!
                            selectedProperty = properties[0]
                        }
                        ExposedDropdownMenu(expanded = propertyExpanded, onDismissRequest = { propertyExpanded = false }) {
                            for (property in properties) {
                                val propertyAddress = property.straat!! + " " + property.huisnummer!! + ", " + property.postcode!! + " " + property.stad!!
                                DropdownMenuItem(onClick = {
                                    issuePropertyAddress = propertyAddress
                                    selectedProperty = property
                                    propertyExpanded = false
                                }) {
                                    Text(text = propertyAddress)
                                }
                            }
                        }
                    }
                    else -> Text(text = "No properties found")
                }
            }

        Spacer(modifier = Modifier.padding(10.dp))
            
        Button(
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MainGroen,
                contentColor = Color.White
            ),
            onClick = {
                viewModel.addAnnouncement(selectedProperty.propertyId!!, text)
                onSave()
            },
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .padding(16.dp),
            enabled = selectedProperty.propertyId != null
        ) {
            Text("Save")
        }
        }
    }
}


package com.ugnet.sel1.ui.announcements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.ugnet.sel1.domain.models.Property
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.ui.components.PropertyList
import com.ugnet.sel1.ui.theme.AccentLicht
import com.ugnet.sel1.ui.theme.MainGroen


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AnnouncementsAddScreen(
    viewModel: AnnouncementsViewModel = hiltViewModel(),
) {
    var text by remember { mutableStateOf("") }
    var dialogVisible by remember { mutableStateOf(false) }
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

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Announcement") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        
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
                    if (properties.isNotEmpty()) {
                        issuePropertyAddress = properties[0].straat!! + " " + properties[0].huisnummer!! + ", " + properties[0].postcode!! + " " + properties[0].stad!!
                    }
                    ExposedDropdownMenu(expanded = propertyExpanded, onDismissRequest = { propertyExpanded = false }) {
                        for (property in properties) {
                            val propertyAddress = property.straat!! + " " + property.huisnummer!! + ", " + property.postcode!! + " " + property.stad!!
                            DropdownMenuItem(onClick = {
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


        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { viewModel.addAnnouncement(selectedProperty.propertyId!!, text) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Save")
        }
    }


/* if (dialogVisible) {
        Dialog(onDismissRequest = { dialogVisible = false }) {  // Hide the dialog when dismissed
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(200.dp)
                    .background(Color.White)
            ) {
                when (val uiState = viewModel.uiState.collectAsState().value) {
                    AnnouncementUiState.Loading -> {
                        // Show loading state UI
                    }
                    is AnnouncementUiState.Success -> {
                        val properties = uiState.ownedProperties
                        if (properties.isEmpty()) {
                            Text(text = "No properties found")
                        } else {
                            PropertyList(
                                properties = properties,
                                onPropertyClicked = { property ->
                                    selectedProperty = property
                                    dialogVisible = false
                                }
                            )
                        }
                    }
                }
            }
        }
    }*/

}


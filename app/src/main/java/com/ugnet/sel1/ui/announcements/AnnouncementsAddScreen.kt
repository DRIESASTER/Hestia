package com.ugnet.sel1.ui.announcements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.ugnet.sel1.domain.models.Property
import com.ugnet.sel1.ui.components.PropertyList
import com.ugnet.sel1.ui.theme.MainGroen


@Composable
fun AnnouncementsAddScreen(
    viewModel: AnnouncementsViewModel,
    onDismiss: () -> Unit
) {
    var text by remember { mutableStateOf("") }
    var dialogVisible by remember { mutableStateOf(false) }
    var selectedProperty by remember { mutableStateOf(Property()) }

    Column(modifier = Modifier.fillMaxSize()) {
        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Announcement") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        Text(
            "Selected property: $selectedProperty",
            modifier = Modifier.padding(16.dp)
        )  // Display the selected property

        Button(
            onClick = { dialogVisible = true },  // Show the dialog when the button is clicked
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Select property")
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

    if (dialogVisible) {
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
                        CircularProgressIndicator(color= MainGroen)
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
    }
}


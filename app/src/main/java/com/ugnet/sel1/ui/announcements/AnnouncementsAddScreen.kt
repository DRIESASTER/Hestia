package com.ugnet.sel1.ui.announcements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Absolute.Center
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.ugnet.sel1.domain.models.Property
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.navigation.MyDestinations
import com.ugnet.sel1.ui.components.PropertyList
import com.ugnet.sel1.ui.manager.PropertiesOverview
import com.ugnet.sel1.ui.manager.PropertyOverview
import com.ugnet.sel1.ui.theme.AccentLicht
import com.ugnet.sel1.ui.theme.MainGroen


@Composable
fun AnnouncementsAddScreen(
    viewModel: AnnouncementsViewModel,
    onDismiss: () -> Unit
) {
    var text by remember { mutableStateOf("") }
    var dialogVisible by remember { mutableStateOf(false) }
    var selectedProperty by remember { mutableStateOf(Property()) }

    val propertiesResponse by viewModel.propertiesData.collectAsState(initial = Response.Loading)

    Surface(
        shape = RoundedCornerShape(8.dp),
        elevation = 8.dp,
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .fillMaxHeight(0.8f)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Announcement") },
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            )

            Text("Selected property: $selectedProperty", modifier = Modifier.padding(16.dp))  // Display the selected property

            Button(
                onClick = { dialogVisible = true },  // Show the dialog when the button is clicked
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) {
                Text("Select property")
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { viewModel.addAnnouncement(selectedProperty.propertyId!!, text) },
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) {
                Text("Save")
            }
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
                when(val response = propertiesResponse){
                    is Response.Success -> {
                        if (response.data.isEmpty()) {
                            Text(text = "No properties found")
                        } else {
                            PropertyList(properties = response.data, onPropertyClicked = { property ->
                                selectedProperty = property
                                dialogVisible = false
                            })
                        }
                    }
                    else -> {
                        CircularProgressIndicator(backgroundColor = MainGroen,color = AccentLicht)
                    }
                }
            }
        }
    }
}

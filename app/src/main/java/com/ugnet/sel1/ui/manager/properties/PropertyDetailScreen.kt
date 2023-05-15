package com.ugnet.sel1.ui.manager.properties

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.BorderColor
import androidx.compose.material.icons.rounded.LocationCity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ugnet.sel1.domain.models.Property
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.navigation.MyDestinations
import com.ugnet.sel1.ui.components.RoomDetailOverview
import com.ugnet.sel1.ui.components.SimpleTopBar
import com.ugnet.sel1.ui.theme.MainGroen


@Composable
fun PropertyDetailRoute(
    viewModel: PropertyDetailVM,
    navigateBack: () -> Unit,
    navigate : (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        is Response.Loading -> CircularProgressIndicator()
        is Response.Failure -> Text(text = "Failed")
        is Response.Success -> {
            val propertyData = (uiState as Response.Success<Property?>).data
            if (propertyData != null) {
                PropertyDetailsScreen(
                    propertyData,
                    viewModel = viewModel,
                    navigateBack = { navigateBack() },
                    navigate = { route -> navigate(route) }
                )
            } else {
                Text(text = "Property not found")
            }
        }
    }
}


@Composable
fun PropertyDetailsScreen(
    property: Property,
    viewModel: PropertyDetailVM,
    navigateBack: () -> Unit,
    navigate: (String) -> Unit
) {
    Column() {
        SimpleTopBar(
            name = property.straat+" "+property.huisnummer+", "+property.postcode+" "+property.stad, navigate = { navigateBack() },
            icon = Icons.Rounded.LocationCity)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Location:", fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            //TODO
            //mapview
            Text("no money for google maps api")
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Rooms:", fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                IconButton(onClick ={
                    val route = if(property.type == "house") {
                        Log.d("viewmodel tenant", property.huurders[0])
                        "${MyDestinations.ROOM_EDIT_ROUTE_HOUSE}/${property.huurders[0]}/${property.propertyId}"
                    }else {
                        "${MyDestinations.ROOM_EDIT_ROUTE_APP}/ /${property.propertyId}"
                    }
                    navigate(route)}) {
                    Icon(imageVector = Icons.Rounded.BorderColor, contentDescription = "edit", tint = MainGroen)
                }
            }
            viewModel.getRooms().collectAsState(initial = Response.Loading).value.let { rooms ->
                when (rooms) {
                    is Response.Success -> {
                        RoomDetailOverview(rooms = rooms.data, viewmodel = viewModel)
                    }
                    else -> {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

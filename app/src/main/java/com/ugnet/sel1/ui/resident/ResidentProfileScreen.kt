package com.ugnet.sel1.ui.resident

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.ui.components.ProfileCard
import com.ugnet.sel1.ui.components.PropertyAccesCard
import com.ugnet.sel1.ui.theme.AccentLicht
import com.ugnet.sel1.ui.theme.MainGroen

@Composable
fun ResidentProfileScreen(viewModel: ResidentHomeVM) {
    Column (
        modifier = Modifier.padding(all= 5.dp)
    ) {
        viewModel.getCurrentUser().collectAsState(initial = Response.Loading).value.let { user ->
            when(user) {
                is Response.Success -> {
                    ProfileCard(name = user.data?.voornaam +" "+ (user.data?.achternaam ?: ""),
                                email = user.data?.email!!)
                }
                else -> {
                    CircularProgressIndicator(backgroundColor = MainGroen,color = AccentLicht)
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Rented properties", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        when(val response = viewModel.allRentedPropertiesResponse) {
            is Response.Success -> {
                if (response.data.isEmpty()) {
                    Text(text = "You are currently not renting any properties", fontSize = 16.sp, color = Color.Gray.copy(alpha = 0.5f))
                } else {
                    LazyColumn(modifier= Modifier.fillMaxHeight(0.6f),content = {
                        items(response.data.size) { index ->
                            viewModel.getAccesibleRoomsForProperty(response.data[index].propertyId!!).collectAsState(initial = Response.Loading).value.let {rooms ->
                                when(rooms) {
                                    is Response.Success -> {
                                        PropertyAccesCard(property = response.data[index], accessibleRooms = rooms.data)
                                    }
                                    else -> {
                                        CircularProgressIndicator(backgroundColor = MainGroen,color = AccentLicht)
                                    }
                                }
                            }
                        }
                    })
                }
            }
            else -> {
                CircularProgressIndicator(backgroundColor = MainGroen,color = AccentLicht)
            }
        }
    }
}
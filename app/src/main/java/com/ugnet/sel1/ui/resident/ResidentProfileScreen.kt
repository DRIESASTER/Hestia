package com.ugnet.sel1.ui.resident

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
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
import com.ugnet.sel1.ui.theme.AccentLicht
import com.ugnet.sel1.ui.theme.MainGroen

@Composable
fun ResidentProfileScreen(viewModel: ResidentHomeVM) {
    Column (
        modifier = Modifier.padding(all= 20.dp)
    ) {
        Text(text = "Personal details", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = MainGroen)
        viewModel.getCurrentUser().collectAsState(initial = Response.Loading).value.let { user ->
            when(user) {
                is Response.Success -> {
                    val fstName = "first name: " + user.data!!.voornaam
                    val surName = "surname: " + user.data!!.achternaam
                    val email = "email address: " + user.data!!.email
                    Text(text = fstName, fontSize = 16.sp)
                    Text(text = surName, fontSize = 16.sp)
                    Text(text = email, fontSize = 16.sp)
                }
                else -> {
                    CircularProgressIndicator(backgroundColor = MainGroen,color = AccentLicht)
                }
            }
        }

        Text(text = "Rented properties", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = MainGroen)
        when(val response = viewModel.allRentedPropertiesResponse) {
            is Response.Success -> {
                if (response.data.isEmpty()) {
                    Text(text = "You are currently not renting any properties", fontSize = 16.sp, color = Color.Gray.copy(alpha = 0.5f))
                } else {
                    for (property in response.data) {
                        val propAddress = property.straat!!+" "+property.huisnummer!!+", "+property.postcode!!+" "+property.stad!!
                        Text(text = propAddress, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = MainGroen)
                        viewModel.getAccesibleRoomsForProperty(property.propertyId!!).collectAsState(initial = Response.Loading).value.let {rooms ->
                            when(rooms) {
                                is Response.Success -> {
                                    if (rooms.data.isEmpty()) {
                                        Text(text = "You currently have no access to rooms in this property", fontSize = 16.sp, color = Color.Gray.copy(alpha = 0.5f))
                                    } else {
                                        Text(text = "Accessible rooms", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = AccentLicht)
                                        for (room in rooms.data) {
                                            Text(text = room.naam!!, fontSize = 16.sp)
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
            }
            else -> {
                CircularProgressIndicator(backgroundColor = MainGroen,color = AccentLicht)
            }
        }
    }
}
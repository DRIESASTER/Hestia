package com.ugnet.sel1.ui.resident

import androidx.compose.foundation.layout.Column
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.ui.theme.AccentLicht
import com.ugnet.sel1.ui.theme.MainGroen

@Composable
fun ResidentProfileScreen(viewModel: ResidentHomeVM) {
    Column (
        modifier = Modifier
    ) {
        Text(text = "Personal details", fontWeight = FontWeight.Bold, fontSize = 20.sp)
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
    }
}
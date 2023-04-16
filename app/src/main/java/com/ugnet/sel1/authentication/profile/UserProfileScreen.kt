package com.ugnet.sel1.authentication.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.ugnet.sel1.domain.models.Manager
import com.ugnet.sel1.domain.models.Response


@Composable
fun UserProfileScreen(userViewModel: UserViewModel = hiltViewModel()) {
    val userData = userViewModel.userData.collectAsState(initial = Response.Loading)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (val response = userData.value) {
            is Response.Loading -> {
                CircularProgressIndicator()
            }
            is Response.Success -> {
                response.data?.let { user ->
                    Text(text = "Name: ${user.voornaam}", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Email: ${user.email}", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Role: ${user.achternaam}", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                }
            }
            is Response.Failure -> {
                Text(text = "Error: ${response.e?.localizedMessage}", color = Color.Red)
            }
        }
    }
}


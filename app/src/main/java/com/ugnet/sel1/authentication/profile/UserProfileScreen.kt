package com.ugnet.sel1.authentication.profile

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ugnet.sel1.domain.models.Manager
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.domain.models.User
import com.ugnet.sel1.navigation.MyDestinations


@Composable
fun UserProfileScreen(userViewModel: UserViewModel = hiltViewModel(),
                      navController: NavController) {
//    Log.d("USER", Firebase.auth.currentUser?.uid.toString())

    fun navigateToHomeScreen(user : User) {
        when (user.accountType) {
            "Manager" -> navController.navigate(MyDestinations.MANAGER_HOME_ROUTE)
            "Huurder" -> navController.navigate(MyDestinations.HIREE_HOME_ROUTE)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        userViewModel.getUser(Firebase.auth.currentUser?.uid.toString())
        when (val response = userViewModel.userDataResponse) {
            is Response.Loading -> {
                CircularProgressIndicator()
            }
            is Response.Success -> {
                Log.d("resposne succes", response.data.toString())
                val user = response.data
                response.data?.let { user ->
                    Text(text = "Name: ${user.voornaam}", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Email: ${user.email}", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Achternaam: ${user.achternaam}", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Role: ${user.accountType}", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                }
                Button(
                    onClick = { userViewModel.signOut()
                              navController.navigate(MyDestinations.ROLE_SELECTION_ROUTE)},
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Sign Out", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
                Button(
                    onClick = {
                        if (user != null) {
                            navigateToHomeScreen(user = user)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Home Screen", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
            is Response.Failure -> {
                Text(text = "Error: ${response.e?.localizedMessage}", color = Color.Red)
            }
        }
    }
}






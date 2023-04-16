package com.ugnet.sel1.authentication.login

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ugnet.sel1.authentication.login.SignInViewModel
import com.ugnet.sel1.authentication.login.components.SignIn
import com.ugnet.sel1.navigation.MyDestinations
import com.ugnet.sel1.ui.components.showMessage


@Composable
fun LoginScreen(
    signInViewModel: SignInViewModel,
    navController: NavController,
    login : (email: String, password: String) -> Unit
) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Login", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))



        Button(
            onClick = { login(email.value, password.value)  },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(
            onClick = { navController.navigate(MyDestinations.SIGN_UP_ROUTE) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Don't have an account? Sign Up")
        }
        SignIn(
            showErrorMessage = { errorMessage ->
                if (errorMessage != null) {
                    showMessage(context, errorMessage)
                }
            },
        )
    }
}
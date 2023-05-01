package com.ugnet.sel1.authentication.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ugnet.sel1.authentication.components.HestiaTextField
import com.ugnet.sel1.authentication.components.PasswordTextField
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.navigation.MyDestinations
import com.ugnet.sel1.ui.theme.AccentLicht
import com.ugnet.sel1.ui.theme.MainGroen


@Composable
fun LoginScreen(
    navigate: (String) -> Unit,
    viewModel: SignInViewModel = hiltViewModel()
) {


    val state by viewModel.uiState
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Login", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = MainGroen)
            Spacer(modifier = Modifier.height(16.dp))

            HestiaTextField(
                value = state.email,
                onValueChange = { newValue -> viewModel.onEmailChange(newValue) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Email", color = MainGroen) }
            )

            Spacer(modifier = Modifier.height(8.dp))

            PasswordTextField(
                value = state.password,
                onValueChange = { newValue -> viewModel.onPasswordChange(newValue) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Password", color = MainGroen) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { viewModel.onSignInClick(navigate) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(backgroundColor = MainGroen),
                enabled = state.email.isNotEmpty() && state.password.isNotEmpty()
            ) {
                Text("Login", color = AccentLicht)
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(
                onClick = { navigate(MyDestinations.SIGN_UP_ROUTE) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.textButtonColors(contentColor = MainGroen)
            ) {
                Text("Don't have an account? Sign Up")
            }

            TextButton(
                onClick = { navigate(MyDestinations.FORGOT_PASSWORD_ROUTE) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.textButtonColors(contentColor = MainGroen)
            ) {
                Text("Forgot password?")
            }

            // Show CircularProgressIndicator when signInResponse is Response.Loading
            if (viewModel.signInResponse is Response.Loading) {
                Spacer(modifier = Modifier.height(16.dp))
                CircularProgressIndicator(color = MainGroen)
            }
        }
    }
}










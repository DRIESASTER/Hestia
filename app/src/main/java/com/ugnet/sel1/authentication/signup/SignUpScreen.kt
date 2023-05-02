package com.ugnet.sel1.authentication.signup

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
/*@Composable
fun SingUpRoute(
    viewModel: SignUpViewModel = hiltViewModel(),
    openAndPopUp: (String, String) -> Unit,
    role: State<String?>,
){
    val state by viewModel.uiState
    val context = LocalContext.current

    val scrollState = rememberScrollState()
    when(viewModel.signUpResponse) {
        is Response.Loading -> CircularProgressIndicator()
        is Response.Success -> SignUpScreen


}*/

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    openAndPopUp: (String, String) -> Unit,
    role: State<String?>,
) {
    val state by viewModel.uiState
    val context = LocalContext.current

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
            Text(
                text = "Create Account",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MainGroen
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Replace the OutlinedTextField for name and surname with your desired implementation

            HestiaTextField(
                value = state.name,
                onValueChange = { newValue -> viewModel.onNameChange(newValue) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Name", color = MainGroen) }
            )


            HestiaTextField(
                value = state.surname,
                onValueChange = { newValue -> viewModel.onSurnameChange(newValue) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Surname", color = MainGroen) }
            )

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
                onClick = {
                    println("Button clicked")
                    viewModel.signUpWithEmailAndPassword(
                        role.value!!,
                        openAndPopUp
                    )
                },
                enabled = viewModel.signUpResponse !is Response.Loading,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(backgroundColor = MainGroen)
            ) {
                Text("Create Account", color = AccentLicht)
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(
                onClick = {
                    openAndPopUp(
                        MyDestinations.LOGIN_ROUTE,
                        MyDestinations.SIGN_UP_ROUTE
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = viewModel.signUpResponse !is Response.Loading,
                colors = ButtonDefaults.textButtonColors(contentColor = MainGroen)
            ) {
                Text("Already have an account? Login")
            }
            if (viewModel.signUpResponse is Response.Loading) {
                CircularProgressIndicator(
                )
            }


        }
    }
}


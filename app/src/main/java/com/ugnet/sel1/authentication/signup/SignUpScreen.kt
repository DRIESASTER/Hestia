package com.ugnet.sel1.authentication.signup

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ugnet.sel1.authentication.selection.RoleSelectionViewModel
import com.ugnet.sel1.authentication.signup.components.SignUp
import com.ugnet.sel1.navigation.MyDestinations
import com.ugnet.sel1.ui.components.showMessage
import com.ugnet.sel1.ui.theme.AccentLicht
import com.ugnet.sel1.ui.theme.MainGroen

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    openAndPopUp: (String, String) -> Unit,
    role: State<String?>,
) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val surname = remember { mutableStateOf("") }
    val name = remember { mutableStateOf("") }
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

            OutlinedTextField(
                value = name.value,
                onValueChange = { name.value = it },
                label = { Text("Name", color = MainGroen) },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MainGroen,
                    unfocusedBorderColor = MainGroen,
                    cursorColor = MainGroen
                )
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = surname.value,
                onValueChange = { surname.value = it },
                label = { Text("Surname", color = MainGroen) },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MainGroen,
                    unfocusedBorderColor = MainGroen,
                    cursorColor = MainGroen
                )
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = email.value,
                onValueChange = { email.value = it },
                label = { Text("Email", color = MainGroen) },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MainGroen,
                    unfocusedBorderColor = MainGroen,
                    cursorColor = MainGroen
                )
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = password.value,
                onValueChange = { password.value = it },
                label = { Text("Password", color = MainGroen) },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MainGroen,
                    unfocusedBorderColor = MainGroen,
                    cursorColor = MainGroen
                )
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    println("Button clicked")
                    viewModel.signUpWithEmailAndPassword(
                        email.value,
                        password.value,
                        role.value!!,
                        name.value,
                        surname.value,
                        openAndPopUp
                    )
                },
                modifier = Modifier.fillMaxWidth
                    (),
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
                colors = ButtonDefaults.textButtonColors(contentColor = MainGroen)
            ) {
                Text("Already have an account? Login")
            }

            SignUp(
                sendEmailVerification = {
                    viewModel.sendEmailVerification()
                },
                showVerifyEmailMessage = {
                    showMessage(context, "Verify email")
                },
            )
        }
    }
}


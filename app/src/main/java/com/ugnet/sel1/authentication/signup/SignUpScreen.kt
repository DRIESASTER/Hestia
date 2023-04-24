package com.ugnet.sel1.authentication.signup

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton

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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Create Account", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = name.value,
            onValueChange = { name.value = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = surname.value,
            onValueChange = { surname.value = it },
            label = { Text("Surname") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

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
            onClick = {
                println("Button clicked")
                role.value?.let {
                    viewModel.signUpWithEmailAndPassword(
                        email.value,
                        password.value,
                        it,
                        name.value,
                        surname.value,
                        openAndPopUp
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Create Account")
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(
            onClick = { openAndPopUp(MyDestinations.LOGIN_ROUTE, MyDestinations.SIGN_UP_ROUTE) },
            modifier = Modifier.fillMaxWidth()
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


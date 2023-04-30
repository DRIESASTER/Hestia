package com.ugnet.sel1.authentication.forgot_password

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.livedata.observeAsState
import com.ugnet.sel1.ui.theme.MainGroen


@Composable
fun ForgotPasswordScreen(
    viewModel: ForgotPasswordViewModel = hiltViewModel(),
    navigateTo: (String) -> Unit
) {

    val email by viewModel.email.observeAsState("")
    val emailError by viewModel.emailError.observeAsState(null)
    val isError = emailError != null

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Reset Password", style = MaterialTheme.typography.h4)

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { viewModel.onEmailChange(it) },
                label = {
                    Text(
                        text = "Email",
                        color = if (isError) MaterialTheme.colors.error else MaterialTheme.colors.onSurface
                    )
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(0.8f),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = MaterialTheme.colors.onSurface,
                    focusedIndicatorColor = if (isError) MaterialTheme.colors.error else MainGroen,
                    unfocusedIndicatorColor = if (isError) MaterialTheme.colors.error else MaterialTheme.colors.onSurface.copy(alpha = 0.12f)
                ),
                trailingIcon = emailError?.let {
                    {
                        Icon(Icons.Default.Error, contentDescription = "Error", tint = MaterialTheme.colors.error)
                    }
                }
            )

            if (isError) {
                Text(
                    text = emailError ?: "",
                    color = MaterialTheme.colors.error,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { viewModel.onResetPasswordClick(navigateTo) },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MainGroen
                )
                ) {
                Text(text = "Reset Password")
            }
        }
    }
}
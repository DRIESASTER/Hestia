package com.ugnet.sel1.authentication.signup.components

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.ugnet.sel1.authentication.signup.SignUpViewModel
import com.ugnet.sel1.domain.models.Response

@Composable
fun SignUp(
    viewModel: SignUpViewModel = hiltViewModel(),
    sendEmailVerification: () -> Unit,
    showVerifyEmailMessage: () -> Unit
) {
    when(val signUpResponse = viewModel.signUpResponse) {
        is Response.Loading -> Text(text = "loading")
        is Response.Success -> {
            val isUserSignedUp = signUpResponse.data
            LaunchedEffect(isUserSignedUp) {
                if (isUserSignedUp) {
                    sendEmailVerification()
                    showVerifyEmailMessage()
                }
            }
        }
        is Response.Failure -> signUpResponse.apply {
            LaunchedEffect(e) {
                print(e)
            }
        }
    }
}
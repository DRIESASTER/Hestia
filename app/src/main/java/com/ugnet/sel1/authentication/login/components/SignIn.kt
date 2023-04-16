package com.ugnet.sel1.authentication.login.components

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.ugnet.sel1.authentication.login.SignInViewModel
import com.ugnet.sel1.domain.models.Response


@Composable
fun SignIn(
    viewModel: SignInViewModel = hiltViewModel(),
    showErrorMessage: (errorMessage: String?) -> Unit,
) {
    when(val signInResponse = viewModel.signInResponse) {
        is Response.Loading -> Text(text= "Loading")
        is Response.Success -> Unit
        is Response.Failure -> signInResponse.apply {
            LaunchedEffect(e) {
                print(e)
                showErrorMessage(e?.message)
            }
        }
    }
}
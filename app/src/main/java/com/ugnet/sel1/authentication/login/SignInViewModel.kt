package com.ugnet.sel1.authentication.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ugnet.sel1.authentication.isValidEmail
import com.ugnet.sel1.authentication.selection.AuthRepository
import com.ugnet.sel1.authentication.selection.SignInResponse
import com.ugnet.sel1.common.snackbar.SnackbarManager
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.navigation.MyDestinations
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

import com.ugnet.sel1.R.string as AppText
// source https://medium.com/firebase-developers/how-to-authenticate-to-firebase-using-email-and-password-in-jetpack-compose-bd70ca56ea91


@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repo: AuthRepository,
): ViewModel() {


    var signInResponse by mutableStateOf<SignInResponse>(Response.Success(false))
        private set

    fun onSignInClick(email: String, password: String, navigate: (String) -> Unit) = viewModelScope.launch {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage(AppText.email_error)
        } else if (password.isBlank()) {
            SnackbarManager.showMessage(AppText.empty_password_error)
        } else {
            signInResponse = Response.Loading
            signInResponse = withContext(Dispatchers.IO) {
                repo.firebaseSignInWithEmailAndPassword(email, password)
            }
            when (signInResponse) {
                is Response.Success -> {
                    if ((signInResponse as Response.Success<Boolean>).data) {
                        navigate(MyDestinations.SPLASH_ROUTE)
                    } else {
                        SnackbarManager.showMessage(AppText.login_error)
                    }
                }
                is Response.Failure -> {
                    SnackbarManager.showMessage(AppText.login_error)
                }
                else -> {}
            }
        }
    }



    fun signInWithEmailAndPassword(email: String, password: String) = viewModelScope.launch {
        signInResponse = Response.Loading
        signInResponse = repo.firebaseSignInWithEmailAndPassword(email, password)
    }
}
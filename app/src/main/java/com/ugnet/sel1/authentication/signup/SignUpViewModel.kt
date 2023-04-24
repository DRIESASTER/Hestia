package com.ugnet.sel1.authentication.signup

// source https://medium.com/firebase-developers/how-to-authenticate-to-firebase-using-email-and-password-in-jetpack-compose-bd70ca56ea91

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ugnet.sel1.authentication.selection.AuthRepository
import com.ugnet.sel1.authentication.selection.SendEmailVerificationResponse
import com.ugnet.sel1.authentication.selection.SignUpResponse
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.navigation.MyDestinations
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repo: AuthRepository
): ViewModel() {
    var signUpResponse by mutableStateOf<SignUpResponse>(Response.Success(false))
        private set
    var sendEmailVerificationResponse by mutableStateOf<SendEmailVerificationResponse>(
        Response.Success(
            false
        )
    )
        private set

    fun signUpWithEmailAndPassword(
        email: String,
        password: String,
        role: String,
        surname: String,
        name: String,
        openAndPopUp: (String, String) -> Unit
    ) = viewModelScope.launch {
        println("signUpWithEmailAndPassword called")
        signUpResponse = Response.Loading
        println("before calling repo.firebaseSignUpWithEmailAndPassword")
        signUpResponse = repo.firebaseSignUpWithEmailAndPassword(email, password, role, surname, name)
        println("after calling repo.firebaseSignUpWithEmailAndPassword")

        when (signUpResponse) {
            is Response.Success -> {
                openAndPopUp(MyDestinations.LOGIN_ROUTE, MyDestinations.SIGN_UP_ROUTE)
            }
            is Response.Failure -> {
                //openAndPopUp("Error", (signUpResponse as Response.Failure).throwable.localizedMessage ?: "Error signing up")
            }
            else -> { /* Do nothing */ }
        }
    }
    fun sendEmailVerification() = viewModelScope.launch {
        sendEmailVerificationResponse = Response.Loading
        sendEmailVerificationResponse = repo.sendEmailVerification()
    }
}
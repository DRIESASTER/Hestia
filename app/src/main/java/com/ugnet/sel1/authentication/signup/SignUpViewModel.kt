package com.ugnet.sel1.authentication.signup

// source https://medium.com/firebase-developers/how-to-authenticate-to-firebase-using-email-and-password-in-jetpack-compose-bd70ca56ea91

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ugnet.sel1.authentication.isValidEmail
import com.ugnet.sel1.authentication.selection.AuthRepository
import com.ugnet.sel1.authentication.selection.SendEmailVerificationResponse
import com.ugnet.sel1.authentication.selection.SignUpResponse
import com.ugnet.sel1.common.snackbar.SnackbarManager
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.navigation.MyDestinations
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.ugnet.sel1.R.string as AppText


@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repo: AuthRepository
): ViewModel() {

    var uiState = mutableStateOf(SignUpUiState())
        private set

    private val email
        get() = uiState.value.email

    private val name
        get() = uiState.value.name


    private val password: String
        get() = uiState.value.password

    private val surname: String
        get() = uiState.value.surname

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue.filter { !it.isWhitespace()})
    }
    fun onNameChange(newValue: String) {
        uiState.value = uiState.value.copy(name = newValue.filter { !it.isWhitespace()})

    }
    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue.filter { !it.isWhitespace()})

    }
    fun onSurnameChange(newValue: String) {
        uiState.value = uiState.value.copy(surname = newValue.filter { !it.isWhitespace()})

    }

    var signUpResponse by mutableStateOf<SignUpResponse>(Response.Success(false))
        private


    var sendEmailVerificationResponse by mutableStateOf<SendEmailVerificationResponse>(
        Response.Success(
            false
        )
    )
        private set

    fun signUpWithEmailAndPassword(
        role: String,
        openAndPopUp: (String, String) -> Unit
    ) = viewModelScope.launch {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage(AppText.email_error)
        } else if (password.isBlank()) {
            SnackbarManager.showMessage(AppText.empty_password_error)
        } else if(repo.checkIfEmailExists(email)){
            SnackbarManager.showMessage(AppText.email_already_exists_error)
/*        } else if (name.isBlank()) {

        } else if (surname.isBlank()) {

        }*/

        } else {
            signUpResponse = Response.Loading
            signUpResponse =
                repo.firebaseSignUpWithEmailAndPassword(email, password, role, surname, name)

            when (signUpResponse) {
                is Response.Success -> {
                    SnackbarManager.showMessage(AppText.account_created)
                    openAndPopUp(MyDestinations.LOGIN_ROUTE, MyDestinations.SIGN_UP_ROUTE)
                }
                is Response.Failure -> {
                    // todo
                    SnackbarManager.showMessage(AppText.login_error)
                }
                else -> { /* Do nothing */
                }
            }
        }
    }
    fun sendEmailVerification() = viewModelScope.launch {
        sendEmailVerificationResponse = Response.Loading
        sendEmailVerificationResponse = repo.sendEmailVerification()
    }
}
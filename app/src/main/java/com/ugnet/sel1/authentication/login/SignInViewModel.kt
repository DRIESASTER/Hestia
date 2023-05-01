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


@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repo: AuthRepository,
): ViewModel() {

    var uiState = mutableStateOf(LoginUiState())
        private set

    private val email
        get() = uiState.value.email

    private val password
        get() = uiState.value.password

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue.filter { !it.isWhitespace()})
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue.filter { !it.isWhitespace()})
    }


    var signInResponse by mutableStateOf<SignInResponse>(Response.Success(false))
        private set

    fun onSignInClick(navigate: (String) -> Unit) = viewModelScope.launch {
        if (!repo.checkIfEmailExists(email)) {
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
}
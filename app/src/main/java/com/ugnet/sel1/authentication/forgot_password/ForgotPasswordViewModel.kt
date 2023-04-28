package com.ugnet.sel1.authentication.forgot_password

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ugnet.sel1.authentication.selection.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.ugnet.sel1.authentication.isValidEmail
import com.ugnet.sel1.navigation.MyDestinations


@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val repo : AuthRepository,
) : ViewModel() {


    private val _email = MutableLiveData("")

    val email: LiveData<String> = _email

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun onResetPasswordClick(navigateTo: (String) -> Unit, email: String) {
        viewModelScope.launch {
            Log.d("EMail on reset", email)
            if (!email.isValidEmail() || !repo.checkIfEmailExists(email)) {
                // todo snackbarmanager show if unvalid
            } else {
                repo.sendPasswordResetEmail(email)
                navigateTo(MyDestinations.LOGIN_ROUTE)
            }
        }
    }
}
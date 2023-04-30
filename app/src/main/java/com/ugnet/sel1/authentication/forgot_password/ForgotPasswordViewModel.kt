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

    private val _emailError = MutableLiveData<String?>()
    val emailError: LiveData<String?> = _emailError

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun onResetPasswordClick(navigateTo: (String) -> Unit) {
        viewModelScope.launch {
            val email = _email.value ?: ""
            Log.d("onresetpassowrclick", email)
            if (email.isBlank() || !email.isValidEmail()) {
                _emailError.postValue("Please enter a valid email address")
            } else if (!repo.checkIfEmailExists(email)) {
                _emailError.postValue("Email not found")
            } else {
                Log.d("sendPaswwordreset", email)
                repo.sendPasswordResetEmail(email)
                navigateTo(MyDestinations.LOGIN_ROUTE)
            }
        }
    }
}
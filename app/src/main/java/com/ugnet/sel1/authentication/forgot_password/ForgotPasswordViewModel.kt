package com.ugnet.sel1.authentication.forgot_password

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ugnet.sel1.authentication.selection.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/*@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repo: AuthRepository,
): ViewModel() {*/

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val repo : AuthRepository,
) : ViewModel() {


    private val _email = MutableLiveData("")

    val email: LiveData<String> = _email

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun onResetPasswordClick() {
        viewModelScope.launch {
            repo.sendPasswordResetEmail(_email.value!!)
        }
    }
}
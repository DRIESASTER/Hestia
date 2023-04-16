package com.ugnet.sel1.authentication.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ugnet.sel1.authentication.selection.AuthRepository
import com.ugnet.sel1.authentication.selection.SignInResponse
import com.ugnet.sel1.domain.models.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
// source https://medium.com/firebase-developers/how-to-authenticate-to-firebase-using-email-and-password-in-jetpack-compose-bd70ca56ea91


@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repo: AuthRepository
): ViewModel() {

    private val _selectedRole = MutableLiveData<String?>(null)
    val selectedRole: LiveData<String?> = _selectedRole

    fun setSelectedRole(role: String?) {
        _selectedRole.value = role
    }

    var signInResponse by mutableStateOf<SignInResponse>(Response.Success(false))
        private set

    fun signInWithEmailAndPassword(email: String, password: String) = viewModelScope.launch {
        signInResponse = Response.Loading
        signInResponse = repo.firebaseSignInWithEmailAndPassword(email, password)
    }
}
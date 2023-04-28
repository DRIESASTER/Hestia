package com.ugnet.sel1.authentication.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ugnet.sel1.authentication.selection.AuthRepository
import com.ugnet.sel1.authentication.selection.SignInResponse
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.navigation.MyDestinations
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
// source https://medium.com/firebase-developers/how-to-authenticate-to-firebase-using-email-and-password-in-jetpack-compose-bd70ca56ea91


@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repo: AuthRepository,
): ViewModel() {


    var signInResponse by mutableStateOf<SignInResponse>(Response.Success(false))
        private set

    fun onSignInClick(email: String, password: String) =  viewModelScope.launch {
        signInResponse = Response.Loading
        repo.firebaseSignInWithEmailAndPassword(email, password)
    }



    fun signInWithEmailAndPassword(email: String, password: String) = viewModelScope.launch {
        signInResponse = Response.Loading
        signInResponse = repo.firebaseSignInWithEmailAndPassword(email, password)
    }
}
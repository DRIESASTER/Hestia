package com.ugnet.sel1.authentication.profile

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ugnet.sel1.authentication.selection.AuthRepository
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.domain.repository.UserResponse

import com.ugnet.sel1.domain.useCases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class UserViewModel @Inject constructor(
    private val useCases: UseCases,
    private val repo: AuthRepository
) : ViewModel() {

    private val _userResponse = MutableStateFlow<UserResponse>(Response.Loading as UserResponse)

    var userDataResponse by mutableStateOf<UserResponse>(Response.Loading)
        private set

    fun signOut() = repo.signOut()

    init {
        Log.d("USER", Firebase.auth.currentUser?.uid.toString())
        Firebase.auth.currentUser?.uid?.let { getUser(it) }
    }

    fun getUser(id: String) = viewModelScope.launch {
        useCases.getUser(id).collect { response ->
            userDataResponse = response
        }
    }
}





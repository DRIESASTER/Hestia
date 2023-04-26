package com.ugnet.sel1

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.ugnet.sel1.authentication.selection.AuthRepository
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.domain.models.User
import com.ugnet.sel1.domain.repository.UserResponse
import com.ugnet.sel1.domain.useCases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repo: AuthRepository,
    private val useCases: UseCases,
): ViewModel() {

    val currentUser: FirebaseUser? get() = repo.currentUser

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> get() = _user

    init {
        getAuthState()
        observeAuthState()

    }

    private val authStateListener = FirebaseAuth.AuthStateListener { auth ->
        if (auth.currentUser != null) {
            auth.currentUser?.let { firebaseUser ->
                getUser(firebaseUser.uid)
            }
        } else {
            _user.value = null
        }
    }


    private fun observeAuthState() {
        Log.d("OBSERVEAUTHSTATCALLED", "ok")
        viewModelScope.launch {
            repo.getAuthState(viewModelScope).collect { isLoggedOut ->
                if (!isLoggedOut) {
                    Log.d("USER LOGGED IN ", "HEEEEEEEEEEEEEEEREEEEEEEEEEEE")
                    currentUser?.let { Log.d("UUSERID", it.uid) }
                    currentUser?.let { firebaseUser ->
                        getUser(firebaseUser.uid)
                    }
                } else {
                    Log.d("USROUT IN ", "HEEEEEEEEEEEEEEEREEEEEEEEEEEE")
                    _user.value = null
                }
            }
        }
    }

    var userResponse by mutableStateOf<UserResponse>(Response.Loading)


    val isEmailVerified get() = repo.currentUser?.isEmailVerified ?: false


    fun getUser(id: String) = viewModelScope.launch {
        useCases.getUser(id).collect { response ->
            userResponse = response
            Log.d("USERRESPONSE", userResponse.toString())
        }
    }

    fun getAuthState() = repo.getAuthState(viewModelScope)

}
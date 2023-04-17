package com.ugnet.sel1.authentication.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.ugnet.sel1.authentication.selection.AuthRepository
import com.ugnet.sel1.authentication.selection.RevokeAccessResponse
import com.ugnet.sel1.domain.models.Manager
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.domain.models.User
import com.ugnet.sel1.domain.models.UserData
import com.ugnet.sel1.domain.repository.UserResponse

import com.ugnet.sel1.domain.repository.UsersRepository
import com.ugnet.sel1.domain.useCases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class UserViewModel @Inject constructor(
    private val useCases: UseCases,
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _userResponse = MutableStateFlow<UserResponse>(Response.Loading as UserResponse)
    val userData: StateFlow<UserResponse> get() = _userResponse

    private val authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
        firebaseAuth.currentUser?.let { getUser(it.uid) }
    }

    init {
        auth.addAuthStateListener(authStateListener)
    }

    fun signOut() = auth.signOut()

    fun getUser(id: String) = viewModelScope.launch {
        useCases.getUser(id).collect { response ->
            _userResponse.value = response
        }
    }

    override fun onCleared() {
        super.onCleared()
        auth.removeAuthStateListener(authStateListener)
    }
}

/*    fun saveUserData(userId: String, name: String, surname: String, email: String, userName: String, role: String) {
        viewModelScope.launch {
            try {
                userRepository.saveUserData(userId, name, surname, email, userName, role)
                _userData.value = Result.success(null)
            } catch (e: Exception) {
                _userData.value = Result.failure(e)
            }
        }
    }*/




package com.ugnet.sel1

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val useCases: UseCases
): ViewModel() {

    //val currentUser: FirebaseUser? get() = repo.currentUser
    init {
        getAuthState()
    }


    val userResponse: StateFlow<UserResponse> = repo.getUserResponseFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Response.Loading)

    fun getAuthState() = repo.getAuthState(viewModelScope)
}
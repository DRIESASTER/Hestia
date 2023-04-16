package com.ugnet.sel1.authentication.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ugnet.sel1.authentication.selection.AuthRepository
import com.ugnet.sel1.authentication.selection.RevokeAccessResponse
import com.ugnet.sel1.domain.models.Manager
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.domain.models.UserData
import com.ugnet.sel1.domain.repository.ManagerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: ManagerRepository,
    private val repo: AuthRepository
) : ViewModel() {

    private val _userData = MutableStateFlow<Result<Any?>>(Result.success(null))
    val userData: StateFlow<Result<Any?>> get() = _userData

    fun signOut() = repo.signOut()

    init {
        repo.currentUser?.let { fetchUserData(it.uid) }
    }

    fun fetchUserData(userId: String) {
/*        viewModelScope.launch {
            userRepository.fetchUserData(userId, role)
                .catch { e -> _userData.value = Result.failure(e) }
                .collect { result -> _userData.value = result }
        }*/
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

}


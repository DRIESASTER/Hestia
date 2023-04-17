package com.ugnet.sel1

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.State
import com.ugnet.sel1.domain.models.User
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class UserSession @Inject constructor() {
    private var _currentUser = mutableStateOf<User?>(null)
    val currentUser: State<User?> get() = _currentUser
    fun updateCurrentUser(user: User?) {
        _currentUser.value = user
    }
}

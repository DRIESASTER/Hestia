package com.ugnet.sel1.data.repositories

import android.util.Log
import com.ugnet.sel1.domain.repository.UserResponse
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetUserRepository @Inject constructor(
    private var userReposity : UsersRepositoryImpl,
    private var authRepo : AuthRepositoryImpl
) {

    val currentUserResponse : Flow<UserResponse> =
        callbackFlow {
            try {

                val id = authRepo.currentUserEmail
                val user = userReposity.getUser(id)
                if (user != null) {
                    trySend(user)
                }
                awaitClose { }
            } catch (e: Exception) {
                close(e)
            }
        }
}
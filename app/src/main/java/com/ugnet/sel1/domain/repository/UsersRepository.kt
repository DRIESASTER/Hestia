package com.ugnet.sel1.domain.repository

import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.domain.models.User
import kotlinx.coroutines.flow.Flow

typealias UserResponse = Response<User?>
typealias AddUserResponse = Response<Boolean>

interface UsersRepository {

    fun getUserFromFirestore(id: String): Flow<UserResponse>

    suspend fun saveUserData(
        userId: String,
        name: String,
        surname: String,
        email: String,
        role: String
    ) : AddUserResponse
}
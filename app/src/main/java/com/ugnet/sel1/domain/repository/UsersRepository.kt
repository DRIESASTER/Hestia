package com.ugnet.sel1.domain.repository

import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.domain.models.User
import kotlinx.coroutines.flow.Flow

typealias UserResponse = Response<User?>

interface UsersRepository {

    fun getUserFromFirestore(id: String): Flow<UserResponse>

}
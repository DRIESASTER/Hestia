package com.ugnet.sel1.domain.useCases

import com.ugnet.sel1.data.repositories.GetUserRepository
import com.ugnet.sel1.domain.models.User
import com.ugnet.sel1.domain.repository.UserResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserNoReponse @Inject constructor(private val getUserRepository: GetUserRepository) {

    operator fun invoke() : Flow<User> {
        return getUserRepository.currentUser
    }

}
package com.ugnet.sel1.domain.useCases

import com.ugnet.sel1.authentication.selection.AuthRepository
import com.ugnet.sel1.data.repositories.GetUserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Logout @Inject constructor(private val authRepository: AuthRepository) {

    operator fun invoke()  {
        return authRepository.signOut()
    }

}
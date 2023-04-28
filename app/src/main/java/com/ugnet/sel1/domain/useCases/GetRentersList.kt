package com.ugnet.sel1.domain.useCases

import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.domain.models.User
import com.ugnet.sel1.domain.repository.PropertiesRepository
import kotlinx.coroutines.flow.Flow

class GetRentersList constructor(
    private val repo: PropertiesRepository
) {
    operator fun invoke(userId:String): Flow<Response<MutableList<User>>> = repo.getRentersList(userId)
}
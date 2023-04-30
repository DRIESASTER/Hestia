package com.ugnet.sel1.domain.useCases

import com.ugnet.sel1.domain.repository.PropertiesRepository
import com.ugnet.sel1.domain.repository.UsersRepository

class AddUserToProperty constructor(
    private val repo: PropertiesRepository
) {
    suspend operator fun invoke(userId:String, propertyId:String) = repo.addUserToProperty(userId, propertyId)
}
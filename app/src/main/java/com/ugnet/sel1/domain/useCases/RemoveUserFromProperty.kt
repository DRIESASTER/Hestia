package com.ugnet.sel1.domain.useCases

import com.ugnet.sel1.domain.repository.PropertiesRepository

class RemoveUserFromProperty constructor(
    private val repo: PropertiesRepository
) {
    suspend operator fun invoke(userId:String, propertyId:String) = repo.removeUserFromProperty(userId, propertyId)
}
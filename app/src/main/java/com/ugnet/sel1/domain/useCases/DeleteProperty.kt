package com.ugnet.sel1.domain.useCases

import com.ugnet.sel1.domain.repository.PropertiesRepository

class DeleteProperty constructor(
    private val repo: PropertiesRepository
) {
    suspend operator fun invoke(propertyId: String) = repo.deletePropertyFromFirestore(propertyId)
}
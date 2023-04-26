package com.ugnet.sel1.domain.useCases

import com.ugnet.sel1.domain.repository.PropertiesRepository
import com.ugnet.sel1.domain.repository.PropertiesResponse
import kotlinx.coroutines.flow.Flow

class GetRentedProperties constructor(
    private val repo: PropertiesRepository
) {
    operator fun invoke(userId:String): Flow<PropertiesResponse> = repo.getRentedPropertiesFromFirestore(userId)
}
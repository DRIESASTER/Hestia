package com.ugnet.sel1.domain.useCases


import com.ugnet.sel1.domain.repository.RoomsRepository
import com.ugnet.sel1.domain.repository.RoomsResponse
import kotlinx.coroutines.flow.Flow


class GetRoomsForProperty constructor(
    private val repo: RoomsRepository
) {
    operator fun invoke(propertyId:String): Flow<RoomsResponse> = repo.getRoomsForPropertyFromFirestore(propertyId)
}
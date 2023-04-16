package com.ugnet.sel1.domain.useCases


import com.ugnet.sel1.domain.repository.RoomsRepository


class GetRoomsForProperty constructor(
    private val repo: RoomsRepository
) {
    operator fun invoke(propertyId:String) = repo.getRoomsForPropertyFromFirestore(propertyId)
}
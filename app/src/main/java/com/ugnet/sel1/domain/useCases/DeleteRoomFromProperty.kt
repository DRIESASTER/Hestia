package com.ugnet.sel1.domain.useCases

import com.ugnet.sel1.domain.repository.RoomsRepository

class DeleteRoomFromProperty constructor(
    private val repo: RoomsRepository
) {
    suspend operator fun invoke(propertyId: String, roomId: String) = repo.deleteRoomFromPropertyInFirestore(propertyId, roomId)
}
package com.ugnet.sel1.domain.useCases

import com.ugnet.sel1.domain.models.Status
import com.ugnet.sel1.domain.repository.RoomsRepository

class AddUserToRoom constructor(
    private val repo: RoomsRepository
) {
    suspend operator fun invoke(propertyId:String, roomId:String, userId:String) = repo.addUserToRoom(propertyId, roomId, userId);
}
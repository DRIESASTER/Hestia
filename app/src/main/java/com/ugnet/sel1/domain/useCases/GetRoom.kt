package com.ugnet.sel1.domain.useCases

import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.domain.models.Room

import com.ugnet.sel1.domain.repository.RoomsRepository
import kotlinx.coroutines.flow.Flow

class GetRoom constructor(
    private val repo: RoomsRepository
) {
    operator fun invoke(propertyId:String, roomId:String): Flow<Response<Room?>> = repo.getRoom(propertyId, roomId)
}
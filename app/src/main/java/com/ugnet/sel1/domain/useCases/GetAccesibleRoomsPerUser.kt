package com.ugnet.sel1.domain.useCases

import com.ugnet.sel1.domain.repository.RoomsRepository

class GetAccesibleRoomsPerUser constructor(
    private val repo: RoomsRepository
) {
    operator fun invoke(id:String) = repo.getRentedRoomsByUserInFirestore(id)
}
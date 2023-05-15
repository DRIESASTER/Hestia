package com.ugnet.sel1.domain.useCases

import com.ugnet.sel1.domain.repository.PropertiesRepository
import com.ugnet.sel1.domain.repository.RoomsRepository

class GetAnnouncementsForManager constructor(
    private val repo: PropertiesRepository

){
    operator fun invoke() = repo.getAllAnnouncementsFromProperties()
}

/*
class GetAccesibleRoomsPerUser constructor(
    private val repo: RoomsRepository
) {
    operator fun invoke(userId:String, propertyId:String) = repo.getAccesibleRoomsByUserInFirestore(userId, propertyId)
}*/

package com.ugnet.sel1.domain.useCases

import com.ugnet.sel1.domain.repository.RoomsRepository

class AddRoomToProperty constructor(
    private val repo: RoomsRepository
) {
    suspend operator fun invoke(propertyId: String, naam: String, huurderLijst:List<String>) = repo.addRoomToPropertyInFirestore(propertyId, naam, huurderLijst)
}
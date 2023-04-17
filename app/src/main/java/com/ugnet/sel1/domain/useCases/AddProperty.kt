package com.ugnet.sel1.domain.useCases

import com.ugnet.sel1.domain.repository.PropertiesRepository

class AddProperty constructor(
    private val repo: PropertiesRepository
) {
    suspend operator fun invoke(huisnummer:Int, type:String, ownedBy:String, postcode:Int, stad:String, straat:String) = repo.addPropertyToFirestore(huisnummer, type, ownedBy, postcode, stad, straat)
}
package com.ugnet.sel1.domain.useCases

import com.ugnet.sel1.domain.repository.PropertiesRepository

class AddProperty constructor(
    private val repo: PropertiesRepository
) {
    suspend operator fun invoke(huisnummer:Int, isHuis:Boolean, ownedBy:String, postcode:Int, stad:String, straat:String) = repo.addPropertyToFirestore(huisnummer, isHuis, ownedBy, postcode, stad, straat)
}
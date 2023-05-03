package com.ugnet.sel1.domain.useCases

import com.ugnet.sel1.domain.repository.PropertiesRepository

class EditProperty constructor(
    private val repo: PropertiesRepository
) {
    suspend operator fun invoke(propertyId:String, huisnummer:Int, type:String, ownedBy:String, postcode:Int, stad:String, straat:String, huurdersLijst:List<String>) = repo.editProperty(propertyId, huisnummer, type, ownedBy, postcode, stad, straat, huurdersLijst)
}
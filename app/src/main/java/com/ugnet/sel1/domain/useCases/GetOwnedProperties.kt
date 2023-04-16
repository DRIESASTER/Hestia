package com.ugnet.sel1.domain.useCases.nieuwUsecases

import com.ugnet.sel1.domain.repository.PropertiesRepository


class GetOwnedProperties constructor(
    private val repo: PropertiesRepository
) {
    operator fun invoke(id:String) = repo.getOwnedPropertiesFromFirestore(id)
}
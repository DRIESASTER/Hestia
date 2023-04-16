package com.ugnet.sel1.domain.useCases.adressen

import com.ugnet.sel1.domain.repository.AdresRepository

class GetAdres constructor(
    private val repo: AdresRepository
) {
    operator fun invoke(id:String) = repo.getAdresFromFirestore(id)
}
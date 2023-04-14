package com.ugnet.sel1.domain.useCases

import com.ugnet.sel1.domain.repository.AdresRepository

class DeleteAdres constructor(
    private val repo: AdresRepository
) {
    suspend operator fun invoke(
        adresId:String
    ) = repo.deleteAdresFromFirestore(adresId)
}
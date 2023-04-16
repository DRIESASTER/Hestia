package com.ugnet.sel1.domain.useCases.oudeShit.panden.adressen

import com.ugnet.sel1.domain.repository.AdresRepository

class AddAdres constructor(
    private val repo: AdresRepository
) {
    suspend operator fun invoke(
        straat: String,
        huisnummer: Int,
        gemeente: String,
        land: String,
        postcode: Int
    ) = repo.addAdrestoFirestore(straat, huisnummer, gemeente, land, postcode)
}
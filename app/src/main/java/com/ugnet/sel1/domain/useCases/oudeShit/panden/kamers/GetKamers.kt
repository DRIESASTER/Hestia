package com.ugnet.sel1.domain.useCases.oudeShit.panden.kamers

import com.ugnet.sel1.domain.repository.AdresRepository
import com.ugnet.sel1.domain.repository.KamersRepository

class GetKamers constructor(
    private val repo: KamersRepository
) {
    operator fun invoke() = repo.getKamersFromFirestore()
}
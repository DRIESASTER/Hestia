package com.ugnet.sel1.domain.useCases.kamers

import com.ugnet.sel1.domain.repository.AdresRepository
import com.ugnet.sel1.domain.repository.KamersRepository

class GetKamer constructor(
    private val repo: KamersRepository
) {
    operator fun invoke(id:String) = repo.getKamerFromFirestore(id)
}
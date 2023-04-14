package com.ugnet.sel1.domain.useCases.panden

import com.ugnet.sel1.domain.repository.AdresRepository
import com.ugnet.sel1.domain.repository.PandenRepository

class GetOwnedPanden constructor(
    private val repo: PandenRepository
) {
//    operator fun invoke() = repo.getOwnedPandenFromFirestore()
}
package com.ugnet.sel1.domain.useCases

import com.ugnet.sel1.domain.repository.AdresRepository
import com.ugnet.sel1.domain.repository.PandenRepository

class GetPand constructor(
    private val repo: PandenRepository
) {
    operator fun invoke(id : String) = repo.getPandFromFirestore(id)
}
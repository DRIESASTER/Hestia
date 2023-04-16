package com.ugnet.sel1.domain.useCases.adressen


import com.ugnet.sel1.domain.repository.AdresRepository

class GetAdreses constructor(
    private val repo: AdresRepository
) {
    operator fun invoke() = repo.getAdresesFromFirestore()
}
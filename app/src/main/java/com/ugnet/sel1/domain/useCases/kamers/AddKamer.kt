package com.ugnet.sel1.domain.useCases.kamers

import com.ugnet.sel1.domain.repository.KamersRepository

class AddKamer constructor(
    private val repo: KamersRepository
) {
    suspend operator fun invoke(
        huurder : String,
        naam : String
    ) = repo.addKamerToFirestore(huurder, naam)
}
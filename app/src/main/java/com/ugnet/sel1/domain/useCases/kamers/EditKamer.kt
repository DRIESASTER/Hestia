package com.ugnet.sel1.domain.useCases.kamers

import com.ugnet.sel1.domain.repository.KamersRepository

class EditKamer constructor(
    private val repo: KamersRepository
) {

    suspend operator fun invoke(
        kamerId : String,
        huurder : String,
        naam : String,
        issues : List<String>
    ) = repo.editKamerFromFirestore(kamerId, huurder, naam, issues)
}
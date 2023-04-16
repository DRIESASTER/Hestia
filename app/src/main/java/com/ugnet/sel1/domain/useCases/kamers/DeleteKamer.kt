package com.ugnet.sel1.domain.useCases.kamers

import com.ugnet.sel1.domain.repository.KamersRepository

class DeleteKamer constructor(
    private val repo: KamersRepository
) {
    suspend operator fun invoke(
        kamerId:String
    ) = repo.deleteKamerFromFirestore(kamerId)
}
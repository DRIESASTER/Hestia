package com.ugnet.sel1.domain.useCases.panden

import com.google.firebase.firestore.FirebaseFirestore
import com.ugnet.sel1.domain.repository.PandenRepository


class DeletePand constructor(
    private val pandRepo : PandenRepository
) {
    suspend operator fun invoke(id:String) = pandRepo.deletePandFromFirestore(id)
}
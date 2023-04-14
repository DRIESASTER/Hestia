package com.ugnet.sel1.data.repositories

import com.google.firebase.firestore.CollectionReference
import com.ugnet.sel1.domain.repository.AdresRepository
import com.ugnet.sel1.domain.repository.PandenRepository
import javax.inject.Inject

class PandenRepositoryImpl @Inject constructor(
    private val pandenRef: CollectionReference
): PandenRepository {

    fun getOwnedPandenFromFirestore() = pandenRef.get()

}
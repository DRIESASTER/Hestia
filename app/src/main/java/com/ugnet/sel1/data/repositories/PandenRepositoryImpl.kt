package com.ugnet.sel1.data.repositories

import com.google.firebase.firestore.CollectionReference
import com.ugnet.sel1.domain.models.Pand
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.domain.repository.PandResponse
import com.ugnet.sel1.domain.repository.PandenRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class PandenRepositoryImpl @Inject constructor(
    private val pandenRef: CollectionReference
): PandenRepository {

    fun getOwnedPandenFromFirestore() = pandenRef.get()


    override fun getPandFromFirestore(id : String) = callbackFlow {
        val snapshotListener = pandenRef.document(id).addSnapshotListener{ snapshot, e ->
            val pandResponse = if (snapshot != null) {
                val pand = snapshot.toObject(Pand::class.java)
                Response.Success(pand)
            } else {
                Response.Failure(e)
            }
            trySend(pandResponse)
        }
        awaitClose { snapshotListener.remove() }
    }
}
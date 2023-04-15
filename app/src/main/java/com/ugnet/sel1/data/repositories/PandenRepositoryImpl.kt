package com.ugnet.sel1.data.repositories

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.ugnet.sel1.domain.models.Pand
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.domain.repository.PandenRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class PandenRepositoryImpl @Inject constructor(
    private val pandenRef: FirebaseFirestore
): PandenRepository {

    fun getOwnedPandenFromFirestore() = pandenRef.collection("panden").get()


    override fun getPandFromFirestore(id : String) = callbackFlow {
        val snapshotListener = pandenRef.collection("panden").document(id).addSnapshotListener{ snapshot, e ->
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



    override fun getPandenFromFirestore() = callbackFlow {
        val snapshotListener = pandenRef.collection("panden").addSnapshotListener{ snapshot, e ->
            val pandenResponse = if (snapshot != null) {
                val panden = snapshot.toObjects(Pand::class.java)
                Response.Success(panden)
            } else {
                Response.Failure(e)
            }
            trySend(pandenResponse)
        }
        awaitClose { snapshotListener.remove() }
    }
}
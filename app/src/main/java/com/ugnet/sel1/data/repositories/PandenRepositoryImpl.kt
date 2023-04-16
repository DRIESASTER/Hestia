package com.ugnet.sel1.data.repositories

import com.google.firebase.firestore.FirebaseFirestore
import com.ugnet.sel1.domain.models.Pand
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.domain.repository.DeletePandResponse
import com.ugnet.sel1.domain.repository.PandenRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PandenRepositoryImpl @Inject constructor(
    private val firestoreDB: FirebaseFirestore
): PandenRepository {

    fun getOwnedPandenFromFirestore() = firestoreDB.collection("panden").get()


    override fun getPandFromFirestore(id : String) = callbackFlow {
        val snapshotListener = firestoreDB.collection("panden").document(id).addSnapshotListener{ snapshot, e ->
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
        val snapshotListener = firestoreDB.collection("panden").addSnapshotListener{ snapshot, e ->
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

    override suspend fun deletePandFromFirestore(id: String): DeletePandResponse {
        return try {
        firestoreDB.collection("panden").document(id).delete().await()
        Response.Success(true)
    } catch (e: Exception) {
        Response.Failure(e)
    }
}
}
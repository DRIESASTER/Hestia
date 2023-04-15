package com.ugnet.sel1.data.repositories

import com.google.firebase.firestore.FirebaseFirestore
import com.ugnet.sel1.domain.models.Manager
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.domain.repository.ManagerRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class ManagerRepositoryImpl @Inject constructor(
    private val managerRef: FirebaseFirestore
): ManagerRepository {
    override fun getManagerFromFirestore(id: String) = callbackFlow {
        val snapshotListener = managerRef.collection("manager").document(id).addSnapshotListener{ snapshot, e ->
            val managerResponse = if (snapshot != null) {
                val manager = snapshot.toObject(Manager::class.java)
                Response.Success(manager)
            } else {
                Response.Failure(e)
            }
            trySend(managerResponse)
        }
        awaitClose { snapshotListener.remove() }
    }

}
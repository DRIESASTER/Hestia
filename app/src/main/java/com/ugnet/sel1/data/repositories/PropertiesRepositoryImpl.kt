package com.ugnet.sel1.data.repositories

import com.google.firebase.firestore.FirebaseFirestore
import com.ugnet.sel1.domain.models.Property
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.domain.repository.PropertiesRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class PropertiesRepositoryImpl @Inject constructor(
    private val firestoreDB: FirebaseFirestore
): PropertiesRepository {


//    override fun getPandFromFirestore(id : String) = callbackFlow {
//        val snapshotListener = firestoreDB.collection("panden").document(id).addSnapshotListener{ snapshot, e ->
//            val pandResponse = if (snapshot != null) {
//                val property = snapshot.toObject(Property::class.java)
//                Response.Success(property)
//            } else {
//                Response.Failure(e)
//            }
//            trySend(pandResponse)
//        }
//        awaitClose { snapshotListener.remove() }
//    }



    override fun getOwnedPropertiesFromFirestore(id:String) = callbackFlow {
        val snapshotListener = firestoreDB.collection("panden").whereEqualTo("ownedBy",id).addSnapshotListener{ snapshot, e ->
            val pandenResponse = if (snapshot != null) {
                val panden = snapshot.toObjects(Property::class.java)
                Response.Success(panden)
            } else {
                Response.Failure(e)
            }
            trySend(pandenResponse)
        }
        awaitClose { snapshotListener.remove() }
    }

//    override suspend fun deletePandFromFirestore(id: String): DeletePandResponse {
//        return try {
//        firestoreDB.collection("panden").document(id).delete().await()
//        Response.Success(true)
//    } catch (e: Exception) {
//        Response.Failure(e)
//    }
//}
}
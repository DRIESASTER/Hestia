package com.ugnet.sel1.data.repositories

import com.google.firebase.firestore.FirebaseFirestore
import com.ugnet.sel1.domain.models.Property
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.domain.repository.AddPropertyResponse
import com.ugnet.sel1.domain.repository.DeletePropertyResponse
import com.ugnet.sel1.domain.repository.PropertiesRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PropertiesRepositoryImpl @Inject constructor(
    private val firestoreDB: FirebaseFirestore
): PropertiesRepository {

    override fun getOwnedPropertiesFromFirestore(id:String) = callbackFlow {
        val snapshotListener = firestoreDB.collection("properties").whereEqualTo("ownedBy",id).addSnapshotListener{ snapshot, e ->
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


    override suspend fun addPropertyToFirestore(
        huisnummer: Int,
        isHuis: Boolean,
        ownedBy: String,
        postcode: Int,
        stad: String,
        straat: String
    ) = callbackFlow {
        val pand = hashMapOf(
            "huisnummer" to huisnummer,
            "isHuis" to isHuis,
            "ownedBy" to ownedBy,
            "postcode" to postcode,
            "stad" to stad,
            "straat" to straat
        )
        val pandRef = firestoreDB.collection("properties").document()
        pandRef.set(pand).addOnSuccessListener {
            trySend(Response.Success(pandRef.id))
        }.addOnFailureListener {
            trySend(Response.Failure(it))
        }
    }

    override suspend fun deletePropertyFromFirestore(propertyId: String): DeletePropertyResponse {
        return try {
            firestoreDB.collection("properties").document(propertyId).delete().await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

}
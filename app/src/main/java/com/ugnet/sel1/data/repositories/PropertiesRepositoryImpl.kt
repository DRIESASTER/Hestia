package com.ugnet.sel1.data.repositories

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.ugnet.sel1.domain.models.Property
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.domain.models.User
import com.ugnet.sel1.domain.repository.AddPropertyResponse
import com.ugnet.sel1.domain.repository.DeletePropertyResponse
import com.ugnet.sel1.domain.repository.PropertiesRepository
import com.ugnet.sel1.domain.useCases.AddProperty
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PropertiesRepositoryImpl @Inject constructor(
    private val dbRef: FirebaseFirestore
): PropertiesRepository {

    override fun getOwnedPropertiesFromFirestore(id:String) = callbackFlow {
        val snapshotListener = dbRef.collection("properties").whereEqualTo("ownedBy",id).addSnapshotListener{ snapshot, e ->
            val propertyResponse = if (snapshot != null) {
                val panden = snapshot.toObjects(Property::class.java)
                Response.Success(panden)
            } else {
                Response.Failure(e)
            }
            trySend(propertyResponse)
        }
        awaitClose { snapshotListener.remove() }
    }


    override suspend fun addPropertyToFirestore(
        huisnummer: Int,
        type: String,
        ownedBy: String,
        postcode: Int,
        stad: String,
        straat: String
    ) : AddPropertyResponse {
        return try {
            var id = dbRef.collection("properties").document().id
            val property = Property(
                huisnummer = huisnummer,
                type = type,
                ownedBy = ownedBy,
                postcode = postcode,
                stad = stad,
                straat = straat,
                propertyId = id
            )
            dbRef.collection("properties").document(id).set(property).await()
            Response.Success(id)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }



//    override suspend fun addPropertyToFirestore(
//        huisnummer: Int,
//        type: String,
//        ownedBy: String,
//        postcode: Int,
//        stad: String,
//        straat: String
//    ) = callbackFlow {
//        val id = dbRef.collection("properties").document().id
//        val property = Property(
//            huisnummer = huisnummer,
//            type = type,
//            ownedBy = ownedBy,
//            postcode = postcode,
//            stad = stad,
//            straat = straat,
//            propertyId = id
//        )
//        val propertyRef = dbRef.document("properties/${id}").set(property).addOnSuccessListener {
//            trySend(Response.Success(id))
//        }.addOnFailureListener {
//            trySend(Response.Failure(it))
//        }

//    }

    override suspend fun deletePropertyFromFirestore(propertyId: String): DeletePropertyResponse {
        return try {
            dbRef.collection("properties").document(propertyId).delete().await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

}
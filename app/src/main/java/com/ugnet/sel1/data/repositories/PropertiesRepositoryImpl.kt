package com.ugnet.sel1.data.repositories

import android.media.Image
import android.util.Log
import com.google.firebase.firestore.FieldValue
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

    override fun getOwnedPropertiesFromFirestore(id: String): Flow<Response<MutableList<Property>>> = callbackFlow {
        val snapshotListener = dbRef.collection("properties").whereEqualTo("ownedBy", id)
            .addSnapshotListener { snapshot, e ->
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

    override fun getRentedPropertiesFromFirestore(id: String): Flow<Response<MutableList<Property>>> = callbackFlow {
        val snapshotListener = dbRef.collection("properties").whereArrayContains("huurders", id)
            .addSnapshotListener { snapshot, e ->
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

    override fun getRentersList(propertyId:String) = callbackFlow {
        val snapshotListener = dbRef.collection("properties/${propertyId}/huurders").addSnapshotListener { snapshot, e ->
            val usersResponse = if (snapshot != null) {
                val users = snapshot.toObjects(User::class.java)
                Response.Success(users)
            } else {
                Response.Failure(e)
            }
            trySend(usersResponse)
        }
        awaitClose { snapshotListener.remove() }
    }

    override fun getProperty(
        propertyId:String
    ) : Flow<Response<Property?>> = callbackFlow {
        val snapshotListener = dbRef.collection("properties").document(propertyId).addSnapshotListener { snapshot, e ->
            val propertyResponse = if (snapshot != null) {
                val property = snapshot.toObject(Property::class.java)
                Response.Success(property)
            } else {
                Response.Failure(e)
            }
            trySend(propertyResponse)
        }
        awaitClose { snapshotListener.remove() }
    }


    override suspend fun editProperty(
        propertyId: String,
        huisnummer: Int,
        type: String,
        ownedBy: String,
        postcode: Int,
        stad: String,
        straat: String,
        huurdersLijst: List<String>
    ): Response<Boolean> {
        return try{
            dbRef.collection("properties").document(propertyId).update("huisnummer", huisnummer).await()
            dbRef.collection("properties").document(propertyId).update("type", type).await()
            dbRef.collection("properties").document(propertyId).update("ownedBy", ownedBy).await()
            dbRef.collection("properties").document(propertyId).update("postcode", postcode).await()
            dbRef.collection("properties").document(propertyId).update("stad", stad).await()
            dbRef.collection("properties").document(propertyId).update("straat", straat).await()
            dbRef.collection("properties").document(propertyId).update("huurders", huurdersLijst).await()
            Response.Success(true)
        } catch(e:Exception) {
            Response.Failure(e)
        }
}


    override suspend fun addPropertyToFirestore(
        huisnummer: Int,
        type: String,
        ownedBy: String,
        postcode: Int,
        stad: String,
        straat: String
    ): AddPropertyResponse {
        return try {
            var id = dbRef.collection("properties").document().id
            val property = Property(
                huisnummer = huisnummer,
                type = type,
                ownedBy = ownedBy,
                postcode = postcode,
                stad = stad,
                straat = straat,
                propertyId = id,
                huurders = mutableListOf()
            )
            dbRef.collection("properties").document(id).set(property).await()
            Response.Success(id)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }



    override suspend fun deletePropertyFromFirestore(propertyId: String): DeletePropertyResponse {
        return try {
            dbRef.collection("properties").document(propertyId).delete().await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun addUserToProperty(userId: String, propertyId: String): Response<Boolean> {
        return try{
            dbRef.collection("properties").document(propertyId).update("huurders", (FieldValue.arrayUnion(userId))).await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun removeUserFromProperty(
        userId: String,
        propertyId: String
    ): Response<Boolean> {
        return try{
            dbRef.collection("properties/${propertyId}").document().update("huurders", FieldValue.arrayRemove(userId)).await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

}
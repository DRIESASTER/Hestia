package com.ugnet.sel1.data.repositories

import android.graphics.BitmapFactory
import android.media.Image
import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.ktx.Firebase
import com.ugnet.sel1.domain.models.*
import com.ugnet.sel1.domain.repository.AddPropertyResponse
import com.ugnet.sel1.domain.repository.DeletePropertyResponse
import com.ugnet.sel1.domain.repository.PropertiesRepository
import com.ugnet.sel1.domain.repository.PropertiesResponse
import com.ugnet.sel1.domain.useCases.AddProperty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.time.ZoneId
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class PropertiesRepositoryImpl @Inject constructor(
    private val dbRef: FirebaseFirestore
): PropertiesRepository {

    override fun getOwnedPropertiesFromFirestore(): Flow<Response<MutableList<Property>>> = callbackFlow {
        val snapshotListener = dbRef.collection("properties").whereEqualTo("ownedBy", Firebase.auth.currentUser!!.email)
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

    override suspend fun propertyExists(id: String): Boolean =
        withContext(Dispatchers.IO) {
            val documentRef = dbRef.collection("properties").document(id)
            try {
                val snapshot = documentRef.get().await()
                snapshot.exists()
            } catch (e: Exception) {
                false
            }
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


    override suspend fun addAnnouncement(propertyId: String, text: String) : Response<Boolean> {
        return try {
            val announcement = Announcement(propertyId, text)
            val data = hashMapOf(
                "propertyId" to announcement.propertyId,
                "text" to announcement.text
            )
            dbRef.collection("properties").document(propertyId).collection("announcements").add(data)
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }


    override fun getOwnedPropertiesNoResponse(): Flow<MutableList<Property>> = callbackFlow {
        try {
            val snapshotListener = dbRef.collection("properties")
                .whereEqualTo("ownedBy", Firebase.auth.currentUser!!.email)
                .addSnapshotListener { snapshot, e ->
                    if (e != null) {
                        close(e)
                    } else if (snapshot != null){
                        val panden = snapshot.toObjects(Property::class.java)
                        trySend(panden)
                    }
                }
            awaitClose { snapshotListener.remove() }
        } catch (e: Exception){
            close(e)
        }
    }

    override fun getAllAnnouncementsFromProperties(): Flow<List<Announcement>> = callbackFlow {
        val announcementListeners = mutableListOf<ListenerRegistration>()
        val allAnnouncements = mutableListOf<Announcement>()

        try {
            val propertiesSnapshotListener = dbRef.collection("properties")
                .whereEqualTo("ownedBy", Firebase.auth.currentUser!!.email)
                .addSnapshotListener { snapshot, e ->
                    if (e != null) {
                        close(e) // Send the exception to the coroutine that is collecting from this Flow
                    } else if (snapshot != null) {
                        val properties = snapshot.toObjects(Property::class.java)
                        properties.forEach { property ->
                            val announcementListener =
                                dbRef.collection("properties/${property.propertyId}/announcements")
                                    .addSnapshotListener { snapshot, e ->
                                        if (e != null) {
                                            close(e)
                                        } else if (snapshot != null) {
                                            val announcements =
                                                snapshot.toObjects(Announcement::class.java)
                                            allAnnouncements.addAll(announcements)
                                            trySend(allAnnouncements)
                                        }
                                    }
                            announcementListeners.add(announcementListener)
                        }
                    }
                }
            awaitClose {
                propertiesSnapshotListener.remove()
                announcementListeners.forEach { it.remove() }
            }
        } catch (e: Exception) {
            close(e)
        }
    }


        override fun getAllAnnouncementsFromRentedProperties(userId: String): Flow<List<Announcement>> = callbackFlow {
        val announcementListeners = mutableListOf<ListenerRegistration>()
        val allAnnouncements = mutableListOf<Announcement>()

        try {
            val propertiesSnapshotListener = dbRef.collection("properties").whereArrayContains("huurders", userId)
                .addSnapshotListener { snapshot, e ->
                    if (e != null) {
                        close(e) // Send the exception to the coroutine that is collecting from this Flow
                    } else if (snapshot != null) {
                        val properties = snapshot.toObjects(Property::class.java)
                        properties.forEach { property ->
                            val announcementListener = dbRef.collection("properties/${property.propertyId}/announcements")
                                .addSnapshotListener { snapshot, e ->
                                    if (e != null) {
                                        close(e)
                                    } else if (snapshot != null) {
                                        val announcements = snapshot.toObjects(Announcement::class.java)
                                        allAnnouncements.addAll(announcements)
                                        trySend(allAnnouncements.toList())
                                    }
                                }
                            announcementListeners.add(announcementListener)
                        }
                    }
                }
            awaitClose {
                propertiesSnapshotListener.remove()
                announcementListeners.forEach { it.remove() }
            }
        } catch (e: Exception) {
            close(e)
        }
    }


/*    override fun getRentedPropertiesFromFirestore(id: String): Flow<Response<MutableList<Property>>> = callbackFlow {
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
    }*/



    fun  getAnnouncementsPerProperty(propertyId: String) : Flow<Response<List<Announcement>>> = callbackFlow {
        val snapshotListener =
            dbRef.collection("properties/${propertyId}/announcements")
                .addSnapshotListener { snapshot, e ->
                    val announcementsResponse = if (snapshot != null) {
                        val announcement = snapshot.toObjects(Announcement::class.java)
                        Response.Success(announcement)
                    } else {
                        Response.Failure(e)
                    }
                    trySend(announcementsResponse)
                }
        awaitClose {
            snapshotListener.remove()
        }
    }


}
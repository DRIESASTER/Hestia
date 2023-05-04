package com.ugnet.sel1.data.repositories

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.ugnet.sel1.domain.models.*
import com.ugnet.sel1.domain.repository.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RoomsRepositoryImpl @Inject constructor(
    private val dbRef: FirebaseFirestore
): RoomsRepository {
    override fun getRoomsForPropertyFromFirestore(propertyId: String) = callbackFlow {
        val snapshotListener =
            dbRef.collection("properties/${propertyId}/rooms").addSnapshotListener { snapshot, e ->
                val roomsResponse = if (snapshot != null) {
                    val rooms = snapshot.toObjects(Room::class.java)
                    Response.Success(rooms)
                } else {
                    Response.Failure(e)
                }
                trySend(roomsResponse)
            }
        awaitClose { snapshotListener.remove() }
    }

    override fun getAccesibleRoomsByUserInFirestore(
        user: String,
        propertyId: String
    ): Flow<RoomsResponse>  = callbackFlow {
        val snapshotListener = dbRef.collection("properties/${propertyId}/rooms").whereArrayContainsAny("huurderLijst", listOf("ALL", user)).addSnapshotListener{snapshot, e ->
            val roomsResponse = if (snapshot != null) {
                val rooms = snapshot.toObjects(Room::class.java)
                Response.Success(rooms)
            } else {
                Response.Failure(e)
            }
            trySend(roomsResponse)
        }
        awaitClose { snapshotListener.remove() }
    }

    override suspend fun addRoomToPropertyInFirestore(
        pandId: String,
        naam: String,
        huurderLijst: List<String>
    ): AddRoomResponse {
        return try {
            val id = dbRef.collection("properties/${pandId}/rooms").document().id
            val room = Room(
                naam = naam,
                roomId = id,
                huurderLijst = huurderLijst
            )
            dbRef.document("properties/${pandId}/rooms/${id}").set(room).await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun addUserToRoom(
        propertyId: String,
        roomId: String,
        userId: String
    ): Response<Boolean> {
        return try {
            dbRef.document("properties/${propertyId}/rooms/${roomId}").update("huurderLijst", FieldValue.arrayUnion(userId)).await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }


    override suspend fun deleteRoomFromPropertyInFirestore(
        pandId: String,
        roomId: String
    ): DeleteRoomResponse {
        return try {
            dbRef.document("properties/${pandId}/rooms/${roomId}").delete().await()
            dbRef.collection("properties/${pandId}/issues").whereEqualTo("roomId", roomId).get()
                .await().documents.forEach { issue ->
                issue.reference.delete().await()
            }
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override fun getRoom(propertyId: String, roomId:String): Flow<Response<Room?>> = callbackFlow {
        val snapshotListener = dbRef.collection("properties/${propertyId}/rooms").document(roomId).addSnapshotListener { snapshot, e ->
            val roomsResponse = if (snapshot != null) {
                val rooms = snapshot.toObject(Room::class.java)
                Response.Success(rooms)
            } else {
                Response.Failure(e)
            }
            trySend(roomsResponse)
        }
        awaitClose { snapshotListener.remove() }
    }
}

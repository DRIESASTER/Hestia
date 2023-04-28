package com.ugnet.sel1.data.repositories

import android.util.Log
import androidx.compose.runtime.collectAsState
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
        val snapshotListener = dbRef.collection("properties/${propertyId}/rooms").whereArrayContainsAny("huurderId", listOf("ALL", user)).addSnapshotListener{snapshot, e ->
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
        huurderId: String?
    ): AddRoomResponse {
        return try {
//            dbRef.collection("properties/${pandId}").document()
//                .update("huurders", FieldValue.arrayUnion((huurderId)))
            Log.d("HIERRRR", "${huurderId},+ ${pandId}")
            val id = dbRef.collection("properties/${pandId}/rooms").document().id
            val room = Room(
                naam = naam,
                roomId = id,
                huurderId = huurderId
            )
            dbRef.document("properties/${pandId}/rooms/${id}").set(room).await()
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

//    override fun getRentedRoomsByUserInFirestore(userId: String): Flow<RoomsResponse> = callbackFlow {
////        var propRef = dbRef.collection("properties").whereArrayContains("huurders", userId).
//        //dbRef.collection("properties").whereArrayContains("huurders", userId).addSnapshotListener()
//        val snapshotListener = dbRef.collectionGroup("rooms").whereEqualTo("huurderId", "gq8qZljKY73A9X3SQzAb").addSnapshotListener() { snapshot, e ->
//            val roomsResponse = if (snapshot != null) {
//                val rooms = snapshot.toObjects(Room::class.java)
//                Response.Success(rooms)
//            } else {
//                Log.d("TAG", "getRentedRoomsByUserInFirestore: ${e?.message}")
//                Response.Failure(e)
//            }
//            trySend(roomsResponse)
//        }
//        awaitClose { snapshotListener.remove()
//    }
//    }


//    override fun getKamersFromFirestore() = callbackFlow {
//        val snapshotListener = kamersRef.collection("kamers").addSnapshotListener { snapshot, e ->
//            val kamersResponse = if (snapshot != null) {
//                val rooms = snapshot.toObjects(Room::class.java)
//                Response.Success(rooms)
//            } else {
//                Response.Failure(e)
//            }
//            trySend(kamersResponse)
//        }
//        awaitClose {
//            snapshotListener.remove()
//        }
//    }
//
//
//    override suspend fun addKamerToFirestore(
//        huurder: String,
//        naam: String
//    ) : AddKamerResponse {
//        return try {
//            val id = kamersRef.collection("kamers").document().id
//            val room = Room(
//                naam = naam,
//                huurder = huurder,
//                issues = emptyList()
//            )
//            kamersRef.document(id).set(room).await()
//            Response.Success(true)
//        } catch (e: Exception) {
//            Response.Failure(e)
//        }
//    }
//
//    override suspend fun deleteKamerFromFirestore(id: String): DeleteKamerResponse {
//        return try {
//            kamersRef.document(id).delete().await()
//            Response.Success(true)
//        } catch (e: Exception) {
//            Response.Failure(e)
//        }
//    }
//
//
//    override suspend fun editKamerFromFirestore(
//        id: String,
//        huurder: String,
//        naam: String,
//        issues: List<String>
//    ): EditKamerResponse {
//        return try {
//            kamersRef.collection("kamers").document(id).update("huurder", huurder).await()
//            kamersRef.collection("kamers").document(id).update("naam", naam).await()
//            kamersRef.collection("kamers").document().update("issues", issues).await()
//            Response.Success(true)
//        } catch (e: Exception) {
//            Response.Failure(e)
//        }

}

package com.ugnet.sel1.data.repositories

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
        val snapshotListener = dbRef.collection("properties/${propertyId}/rooms").addSnapshotListener{ snapshot, e ->
            val roomsResponse = if (snapshot != null) {
                val room = snapshot.toObjects(Room::class.java)
                Response.Success(room)
            } else {
                Response.Failure(e)
            }
            trySend(roomsResponse)
        }
        awaitClose { snapshotListener.remove() }
    }

    override suspend fun addRoomToPropertyInFirestore(pandId: String, naam: String, huurder:String?): AddRoomResponse {
        return try{
            val id = dbRef.collection("properties/${pandId}/rooms").document().id
            val room = Room(
                naam = naam,
                roomId = id,
                huurder = huurder
            )
            dbRef.document("properties/${pandId}/rooms/${id}").set(room).await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }


    override suspend fun deleteRoomFromPropertyInFirestore(pandId: String, roomId: String): DeleteRoomResponse {
        return try {
            dbRef.document("properties/${pandId}/rooms/${roomId}").delete().await()
            dbRef.collection("properties/${pandId}/issues").whereEqualTo("roomId", roomId).get().await().documents.forEach {
                it.reference.delete().await()
            }
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }


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
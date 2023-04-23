package com.ugnet.sel1.data.repositories

import android.util.Log
import com.google.firebase.firestore.FieldValue.serverTimestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.type.Date
import com.ugnet.sel1.domain.models.*
import com.ugnet.sel1.domain.repository.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.time.LocalDateTime
import javax.inject.Inject


class IssuesRepositoryImpl @Inject constructor(
    private val dbRef: FirebaseFirestore
): IssuesRepository {
    override fun getIssuesByRoomFromFirestore(propertyId: String, roomId: String) = callbackFlow {
        val snapshotListener =
            dbRef.collection("properties/${propertyId}/issues").whereEqualTo("roomId", roomId)
                .addSnapshotListener { snapshot, e ->
                    val issuesResponse = if (snapshot != null) {
                        val issues = snapshot.toObjects(Issue::class.java)
                        Response.Success(issues)
                    } else {
                        Response.Failure(e)
                    }
                    trySend(issuesResponse)
                }
        awaitClose {
            snapshotListener.remove()
        }
    }



    override fun getIssuesPerPropertyFromFirestore(
        propertyId: String
    ) = callbackFlow {
        val snapshotListener =
            dbRef.collection("properties/${propertyId}/issues")
                .addSnapshotListener { snapshot, e ->
                    val issuesResponse = if (snapshot != null) {
                        val issues = snapshot.toObjects(Issue::class.java)
                        Response.Success(issues)
                    } else {
                        Response.Failure(e)
                    }
                    trySend(issuesResponse)
                }
        awaitClose {
            snapshotListener.remove()
        }
    }

    override suspend fun addIssueToFirestore(
        beschrijving: String,
        titel: String,
        propertyId: String,
        roomId: String,
        issueType: IssueType) : AddIssueResponse {
        return try{
            val id = dbRef.collection("properties/${propertyId}/issues").document().id
            val issue = Issue(
                beschrijving = beschrijving,
                titel = titel,
                roomId = roomId,
                issueType = issueType,
                status = Status.notStarted,
                datum = null,
                issueId = id
            )
            dbRef.collection("properties/${propertyId}/issues").document(id).set(issue).await()
            Response.Success(id)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }
//    ) = callbackFlow {
//        val id = dbRef.collection("properties/${roomId}/issues").document().id
//        val issue = hashMapOf(
//            "titel" to titel,
//            "beschrijving" to beschrijving,
//            "roomId" to roomId,
//            "issueType" to issueType.toString(),
//            "status" to Status.notStarted.toString(),
//            "datum" to serverTimestamp()
//        )
//        val issueRef =
//            dbRef.document("properties/${roomId}/issues/${id}").set(issue).addOnSuccessListener {
//                trySend(Response.Success(id))
//            }.addOnFailureListener {
//                trySend(Response.Failure(it))
//            }
//    }


        override suspend fun deleteIssueFromFirestore(issueId: String): DeleteIssueResponse {
            return try {
                dbRef.document("properties/issues/${issueId}").delete().await()
                Response.Success(true)
            } catch (e: Exception) {
                Response.Failure(e)
            }
        }


        override suspend fun changeIssueStatusInFirestore(
            issueId: String,
            status: Status,
            propertyId: String
        ): ChangeIssueStatusResponse {
            return try {
                dbRef.collection("properties/${propertyId}/issues").document(issueId)
                    .update("status", status.toString()).await()
                Response.Success(true)
            } catch (e: Exception) {
                Log.d("changeIssueStatus", e.toString())
                Response.Failure(e)
            }
        }

    }


//
//    fun getIssuesByRoomFromFirestore(pandId : String, roomId : String) : IssuesResponse = callbackFlow {
//        val snapshotListener = issuesRef.collection("panden/${pandId}/issues").whereEqualTo("roomId", roomId).addSnapshotListener { snapshot, e ->
//            val issuesResponse = if (snapshot != null) {
//                val issues = snapshot.toObjects(Issue::class.java)
//                Response.Success(issues.filter{ issue -> ids.contains(issue.id)})
//            } else {
//                Response.Failure(e)
//            }
//            trySend(issuesResponse)
//        }
//        awaitClose {
//            snapshotListener.remove()
//        }
//    }
//    }



//    override suspend fun deleteIssueFromFirestore(issueId: String): DeleteIssueResponse {
//        return try {
//            dbRef.collection("issues").document(issueId).delete().await()
//            Response.Success(true)
//        } catch (e: Exception) {
//            Response.Failure(e)
//        }
//    }

//    override suspend fun changeIssueStatus(
//        issueId: String,
//        status: Status
//    ): ChangeIssueStatusResponse {
//        return try {
//            dbRef.collection("issues").document(issueId).update("status", status.toString()).await()
//            Response.Success(true)
//        } catch (e: Exception) {
//            Response.Failure(e)
//        }
//    }
//}
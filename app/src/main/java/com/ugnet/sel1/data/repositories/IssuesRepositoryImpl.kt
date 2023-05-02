package com.ugnet.sel1.data.repositories

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.ugnet.sel1.domain.models.*
import com.ugnet.sel1.domain.repository.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class IssuesRepositoryImpl @Inject constructor(
    private val dbRef: FirebaseFirestore
): IssuesRepository {
    override fun getIssuesByRoomFromFirestore(pandId: String, roomId: String) = callbackFlow {
        val snapshotListener =
            dbRef.collection("properties/${pandId}/issues").whereEqualTo("roomId", roomId)
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

    override fun getIssueMessages(issueId: String): Flow<Response<List<Message>>> = callbackFlow {
        val snapshotListener =
            dbRef.collection("properties/${issueId}/messages").orderBy("timestamp")
                .addSnapshotListener { snapshot, e ->
                    val messageResponse = if (snapshot != null) {
                        val messages = snapshot.toObjects(Message::class.java)
                        Response.Success(messages)
                    } else {
                        Response.Failure(e)
                    }
                    trySend(messageResponse)
                }
        awaitClose {
            snapshotListener.remove()
        }
    }


    override fun sendMessage(issueId: String, message: Message) {
        val newMessage = hashMapOf(
            "senderEmail" to message.senderEmail,
            "messageText" to message.messageText,
            "timestamp" to message.timestamp
        )
        dbRef.collection("properties").document(issueId).collection("messages").add(newMessage)
    }








    override fun getIssue(propId:String, issueId:String): Flow<IssueResponse> = callbackFlow {
        val snapshotListener = dbRef.collection("properties/${propId}/issues").document(issueId).addSnapshotListener{ snapshot, e ->
            val issueResponse = if (snapshot != null) {
                val issue = snapshot.toObject(Issue::class.java)
                Response.Success(issue)
            } else {
                Response.Failure(e)
            }
            trySend(issueResponse as IssueResponse)
        }
        awaitClose { snapshotListener.remove() }
    }



    override fun getIssuesPerPropertyFromFirestore(
        propertyId: String
    ): Flow<IssuesResponse> = callbackFlow {
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




    override fun getIssuesForRenterFromFirestore(
        propertyId: String,
        userId: String
    ): Flow<IssuesResponse> {
        TODO("Not yet implemented")
    }


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

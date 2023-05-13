package com.ugnet.sel1.data.repositories

import android.R
import android.net.Uri
import android.util.Log
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.ugnet.sel1.domain.models.*
import com.ugnet.sel1.domain.repository.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.io.File
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

    override fun getIssueMessages(propId: String, issueId: String): Flow<Response<List<Message>>> =
        callbackFlow {
            val snapshotListener =
                dbRef.collection("properties/${propId}/issues").document(issueId)
                    .collection("messages").orderBy("timestamp")
                    .addSnapshotListener { snapshot, e ->
                        val messageResponse = if (snapshot != null) {
                            val messages = snapshot.toObjects(Message::class.java)
                            Log.d("getIssueMessage", messages.toString())
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


    override fun sendMessage(propId: String, issueId: String, message: Message) {
        val newMessage = hashMapOf(
            "senderEmail" to message.senderEmail,
            "messageText" to message.messageText,
            "timestamp" to message.timestamp
        )
        dbRef.collection("properties/${propId}/issues").document(issueId).collection("messages")
            .add(newMessage)
        //dbRef.collection("properties").document(issueId).collection("messages").add(newMessage)
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
        issueType: IssueType,
        userId: String,
        imageUri:Uri?
    ): AddIssueResponse {
        return try {

            val id = dbRef.collection("properties/${propertyId}/issues").document().id
            var storageRef: StorageReference? = null
            if (imageUri != null) {
                val formatter = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                val date = java.util.Date();
                val filename = formatter.format(date)
                storageRef = FirebaseStorage.getInstance().reference.child("images/${filename}")
                storageRef.putFile(imageUri).await()
            }

            val issue = Issue(
                beschrijving = beschrijving,
                titel = titel,
                roomId = roomId,
                issueType = issueType,
                status = Status.notStarted,
                datum = null,
                issueId = id,
                userId = userId,
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

    override fun getIssue(
        propertyId: String,
        issueId:String
    ): Flow<IssueResponse> = callbackFlow {
        val snapshotListener =
            dbRef.collection("properties/${propertyId}/issues").document(issueId)
                .addSnapshotListener { snapshot, e ->
                    val issueResponse = if (snapshot!= null && snapshot.exists()) {
                        Log.d("HEY WAT IS DIT", "HET BESTAAT")
                        val issue = snapshot.toObject(Issue::class.java)
                        Response.Success(issue)
                    } else {
                        Response.Failure(e)
                    }
                    trySend(issueResponse)
                }
        awaitClose {
            snapshotListener.remove()
        }
    }

    override fun getImage(url:String) = callbackFlow {
        val size: Long = 1024 * 1024 * 20 //20MB
        val storageRef = FirebaseStorage.getInstance().reference.child("images/${url}")
        storageRef.getBytes(size).addOnSuccessListener {
            trySend(Response.Success(it))
        }.addOnFailureListener {
            trySend(Response.Failure(it))
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

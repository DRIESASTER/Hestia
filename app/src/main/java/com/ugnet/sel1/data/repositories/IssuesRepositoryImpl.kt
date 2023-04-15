package com.ugnet.sel1.data.repositories

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.ugnet.sel1.domain.models.Issue
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.domain.models.Status
import com.ugnet.sel1.domain.repository.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class IssuesRepositoryImpl @Inject constructor(
    private val issuesRef: FirebaseFirestore
): IssuesRepository {
    override fun getIssuesFromFirestore(ids:List<String>) = callbackFlow {
        val snapshotListener = issuesRef.collection("issues").addSnapshotListener { snapshot, e ->
            val issuesResponse = if (snapshot != null) {
                val issues = snapshot.toObjects(Issue::class.java)
                Response.Success(issues.filter{ issue -> ids.contains(issue.id)})
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
        datum: Timestamp,
        titel: String
    ): AddIssueResponse {
        return try {
            val id = issuesRef.collection("issues").document().id
            val issue = Issue(
                beschrijving = beschrijving,
                datum = datum,
                titel = titel,
                status = Status.notStarted,
                id = id
            )
            issuesRef.collection("issues").document(id).set(issue).await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }



    override suspend fun deleteIssueFromFirestore(issueId: String): DeleteIssueResponse {
        return try {
            issuesRef.collection("issues").document(issueId).delete().await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun changeIssueStatus(
        issueId: String,
        status: Status
    ): ChangeIssueStatusResponse {
        return try {
            issuesRef.collection("issues").document(issueId).update("status", status.toString()).await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }
}
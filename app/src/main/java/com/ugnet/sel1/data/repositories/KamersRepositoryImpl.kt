package com.ugnet.sel1.data.repositories

import com.google.firebase.Timestamp
import com.google.firebase.firestore.CollectionReference
import com.ugnet.sel1.domain.models.Issue
import com.ugnet.sel1.domain.models.Kamer
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.domain.models.Status
import com.ugnet.sel1.domain.repository.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class KamersRepositoryImpl @Inject constructor(
    private val kamersRef: CollectionReference
): KamersRepository {
    override fun getKamerFromFirestore(id: String) = callbackFlow {
        val snapshotListener = kamersRef.document(id).addSnapshotListener{ snapshot, e ->
            val kamerResponse = if (snapshot != null) {
                val issue = snapshot.toObject(Kamer::class.java)
                Response.Success(issue)
            } else {
                Response.Failure(e)
            }
            trySend(kamerResponse)
        }
        awaitClose { snapshotListener.remove() }
    }


    override suspend fun addKamerToFirestore(
        huurder: String,
        naam: String
    ) : AddKamerResponse {
        return try {
            val id = kamersRef.document().id
            val kamer = Kamer(
                naam = naam,
                huurder = huurder,
                issues = emptyList()
            )
            kamersRef.document(id).set(kamer).await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun deleteKamerFromFirestore(id: String): DeleteKamerResponse {
        return try {
            kamersRef.document(id).delete().await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }


    override suspend fun editKamerFromFirestore(
        id: String,
        huurder: String,
        naam: String,
        issues: List<String>
    ): EditKamerResponse {
        return try {
            kamersRef.document(id).update("huurder", huurder).await()
            kamersRef.document(id).update("naam", naam).await()
            kamersRef.document().update("issues", issues).await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }
}

package com.ugnet.sel1.data.repositories

import com.google.firebase.firestore.FirebaseFirestore
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.domain.models.Room
import com.ugnet.sel1.domain.models.User
import com.ugnet.sel1.domain.repository.AddUserResponse
import com.ugnet.sel1.domain.repository.UsersRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val dbRef: FirebaseFirestore
): UsersRepository {
    override fun getUserFromFirestore(id: String) = callbackFlow {
        val snapshotListener = dbRef.collection("manager").document(id).addSnapshotListener{ snapshot, e ->
            val managerResponse = if (snapshot != null) {
                val manager = snapshot.toObject(User::class.java)
                Response.Success(manager)
            } else {
                Response.Failure(e)
            }
            trySend(managerResponse)
        }
        awaitClose { snapshotListener.remove() }
    }

    override suspend fun saveUserData(userId: String, name: String, surname: String, email: String, userName: String, role: String): AddUserResponse {
        return try{
            val id = dbRef.collection("user").document().id
            val user = User(
                voornaam = name,
                achternaam = surname,
                email = email,
                accountType = role,
                uid = id
            )
            dbRef.document("users/${id}").set(user).await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

}
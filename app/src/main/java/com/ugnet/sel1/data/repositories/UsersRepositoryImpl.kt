package com.ugnet.sel1.data.repositories

import com.google.firebase.firestore.FirebaseFirestore
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.domain.models.User
import com.ugnet.sel1.domain.repository.AddUserResponse
import com.ugnet.sel1.domain.repository.UserResponse
import com.ugnet.sel1.domain.repository.UsersRepository
import com.ugnet.sel1.navigation.AppState
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val dbRef: FirebaseFirestore,
): UsersRepository {
    override fun getUserFromFirestore(id: String) = callbackFlow {
        val snapshotListener = dbRef.collection("users").document(id).addSnapshotListener{ snapshot, e ->
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

    override suspend fun saveUserData(userId: String, name: String, surname: String, email: String, role: String): AddUserResponse {
        return try{
            val user = User(
                voornaam = name,
                achternaam = surname,
                email = email,
                accountType = role,
                uid = userId
            )
            dbRef.document("users/${userId}").set(user).await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override fun getUserByEmail(email:String) = callbackFlow {
        val snapshotListener = dbRef.collection("users").whereEqualTo("email", email).addSnapshotListener{ snapshot, e ->
            val userResponse = if (snapshot != null) {
                val user = snapshot.toObjects(User::class.java)
                Response.Success(user)
            } else {
                Response.Failure(e)
            }
            trySend(userResponse)
        }
        awaitClose { snapshotListener.remove() }
    }



//    fun getRentedRoomsFromFirestore(uid : String) = callbackFlow {
//        val snapshotListener = dbRef.collection("properties").whereArrayContains("huurders", "uid").
//    }

}
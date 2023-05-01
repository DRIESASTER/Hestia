package com.ugnet.sel1.data.repositories


import com.google.firebase.firestore.FirebaseFirestore
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.domain.models.User
import com.ugnet.sel1.domain.repository.AddUserResponse
import com.ugnet.sel1.domain.repository.UserResponse
import com.ugnet.sel1.domain.repository.UsersRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.lang.IllegalArgumentException
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

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

    override suspend fun getUser(userId: String): UserResponse? = suspendCoroutine { continuation ->

        if (userId.isEmpty()) {
            continuation.resume(null)
            return@suspendCoroutine
        }
        dbRef.collection("users").document(userId).get()
            .addOnSuccessListener { result ->
                try {
                    val user = result.toObject(User::class.java)
                    continuation.resume(Response.Success(user))
                } catch (e: IllegalArgumentException) {
                    continuation.resume(null)
                }
            }.addOnFailureListener { exception ->
                continuation.resumeWithException(exception)
            }
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
            dbRef.document("users/${email}").set(user).await()
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
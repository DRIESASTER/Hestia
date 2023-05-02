package com.ugnet.sel1.data.repositories


import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.FirebaseAuthException
import com.ugnet.sel1.authentication.selection.*
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.domain.repository.UsersRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.tasks.await

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val usersRepository: UsersRepository,
) : AuthRepository {

    override val currentUser get() = auth.currentUser


    override val currentUserEmail: String
        get() = auth.currentUser?.email.orEmpty()


    override suspend fun checkIfEmailExists(email: String): Boolean {
        return try {
            val result = auth.fetchSignInMethodsForEmail(email).await()
            result.signInMethods?.isNotEmpty() ?: false
        } catch (e: FirebaseAuthException) {
            if (e.errorCode == "ERROR_USER_NOT_FOUND") {
                false
            } else {
                throw e
            }
        }
    }

    override suspend fun firebaseSignUpWithEmailAndPassword(
        email: String, password: String, role : String, surname : String,
        name : String
    ): SignUpResponse {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            Log.d("firebaseSignup result", result.toString())
            val userId = result.user?.uid ?: throw Exception("User ID not found")
            usersRepository.saveUserData(userId, name, surname,email, role)
            Response.Success(true)
        } catch (e: Exception) {

            Response.Failure(e)
        }
    }



    override suspend fun sendEmailVerification(): SendEmailVerificationResponse {
        return try {
            auth.currentUser?.sendEmailVerification()?.await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun firebaseSignInWithEmailAndPassword(
        email: String, password: String
    ): SignInResponse {
        return try {
            println(email)
            auth.signInWithEmailAndPassword(email, password).await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun reloadFirebaseUser(): ReloadUserResponse {
        return try {
            auth.currentUser?.reload()?.await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun sendPasswordResetEmail(email: String): SendPasswordResetEmailResponse {
        return try {
            auth.sendPasswordResetEmail(email).await()
            Log.d("sendPasswordResetEmail", "succes")
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override fun signOut() = auth.signOut()

    override suspend fun revokeAccess(): RevokeAccessResponse {
        return try {
            auth.currentUser?.delete()?.await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }




    override fun getAuthState(viewModelScope: CoroutineScope) = callbackFlow {
        val authStateListener = AuthStateListener { auth ->
            trySend(auth.currentUser == null)
        }
        auth.addAuthStateListener(authStateListener)
        awaitClose {
            auth.removeAuthStateListener(authStateListener)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), auth.currentUser == null)
}

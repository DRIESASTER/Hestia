package com.ugnet.sel1.data.repositories

import androidx.compose.runtime.snapshots.SnapshotApplyResult
import com.google.firebase.firestore.CollectionReference
import com.ugnet.sel1.domain.models.Adres
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.domain.repository.AddAdresResponse
import javax.inject.Inject
import javax.inject.Singleton
import com.ugnet.sel1.domain.repository.AdresRepository
import com.ugnet.sel1.domain.repository.DeleteAdresResponse
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await


@Singleton
class AdresRepositoryImpl @Inject constructor(
    private val adresRef: CollectionReference
): AdresRepository {
    override fun getAdresesFromFirestore() = callbackFlow {
        val snapshotListener = adresRef.addSnapshotListener { snapshot, e ->
            val adresesResponse = if (snapshot != null) {
                val adreses = snapshot.toObjects(Adres::class.java)
                Response.Success(adreses)
            } else {
                Response.Failure(e)
            }
            trySend(adresesResponse)
        }
        awaitClose {
            snapshotListener.remove()
        }
    }

    override suspend fun addAdrestoFirestore(straat:String, huisnummer:Int, gemeente:String, land:String, postcode:Int): AddAdresResponse {
        return try {
            val id = adresRef.document().id
            val adres = Adres(
                straat = straat,
                huisnummer = huisnummer,
                gemeente = gemeente,
                land = land,
                postcode = postcode
            )
            adresRef.document(id).set(adres).await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }


    override suspend fun deleteAdresFromFirestore(adresId: String): DeleteAdresResponse {
        return try {
            adresRef.document(adresId).delete().await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }
}


//@Singleton
//class AdresRepositoryImpl @Inject constructor(
//    private val backend: AdresBackend,
//) {
//    suspend fun getAdres(id : String): Adres {
//        val adres = backend.readAdres(id)
//        if(adres != null) {
//            return adres
//        }
//        return Adres("", 0, 0, "", "")
//    }
//
//    fun addAdres(straat : String, huisnummer : Int, postcode : Int, gemeente : String, land : String) {
//        backend.writeNewAdres(straat, huisnummer, postcode, gemeente, land)
//    }
//}
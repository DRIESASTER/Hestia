package com.ugnet.sel1.data.repositories

import androidx.compose.runtime.snapshots.SnapshotApplyResult
import com.google.firebase.firestore.CollectionReference
import com.ugnet.sel1.domain.models.Adres
import com.ugnet.sel1.domain.models.Response
import javax.inject.Inject
import javax.inject.Singleton
import com.ugnet.sel1.domain.repository.AdresRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow


@Singleton
class AdresRepositoryImpl @Inject constructor(
    private val adresRef: CollectionReference
): AdresRepository {
    override fun getAdresesFromFirestore() = callbackFlow {
//        val snapshotListener = adresRef.document(id).addSnapshotListener { snapshot, e ->
//            val adresResponse = if (snapshot != null) {
//                val adres = snapshot.toObject(Adres::class.java)
//                Response.Success(adres)
//            } else {
//                Response.Failure(e)
//            }
//            trySend(adresResponse)
//        }
//        awaitClose {
//            snapshotListener.remove()
//        }
//    }
        val snapshotListener = adresRef.addSnapshotListener { snapshot, e ->
            val booksResponse = if (snapshot != null) {
                val books = snapshot.toObjects(Adres::class.java)
                Response.Success(books)
            } else {
                Response.Failure(e)
            }
            trySend(booksResponse)
        }
        awaitClose {
            snapshotListener.remove()
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
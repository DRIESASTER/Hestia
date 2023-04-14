package com.ugnet.sel1.domain.backends

import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.ugnet.sel1.domain.models.Adres
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class AdresBackend @Inject constructor() {


    private lateinit var dbRef: CollectionReference

    init{
        initializeDbRef()
    }


    fun initializeDbRef() {
        // [START initialize_database_ref]
        dbRef = Firebase.firestore.collection("adres")
    // [END initialize_database_ref]
    }

//    fun writeNewAdres(adresId : String, straat : String, huisnummer : Int, postcode : Int, gemeente : String, land : String) {
//        val adres = Adres(straat, huisnummer, postcode, gemeente, land)
//        dbRef.child(adresId).setValue(adres).addOnSuccessListener {
//            // Write was successful!
//            // ...
//        }.addOnFailureListener {
//            // Write failed
//            // ...
//        }
//        // [END rtdb_write_new_user_task]
//    }

    fun writeNewAdres(straat : String, huisnummer : Int, postcode : Int, gemeente : String, land : String) {
//        val key = dbRef.push().key
//        if (key == null) {
//            Log.w("error","Couldn't get push key for adres")
//            return
//        }
//
//        val adres = Adres(straat, huisnummer, postcode, gemeente, land)
//        val adresValues = adres.toMap()
//
//        val childUpdates = hashMapOf<String, Any>(
//            "/adres/$key" to adresValues
//        )
//        dbRef.updateChildren(childUpdates)
    }




    suspend fun readAdres(adresId : String) : Adres? = suspendCoroutine { continuation -> {
        var docRef = Firebase.firestore.collection("adres").document(adresId)
        docRef.get().addOnSuccessListener { document ->
            if (document != null) {
                Log.d("succes", "DocumentSnapshot data: ${document.data}")
                continuation.resume(document.toObject<Adres>())
            } else {
                Log.d("fail", "No such document")
                continuation.resume(null)
            }
        }
    }
    }


// [START rtdb_read_user_task]
//        dbRef.get().addOnSuccessListener { documents ->
//                for (document in documents) {
//                    Log.d("test", "${document.id} => ${document.data}")
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.w("failed", "Error getting documents: ", exception)
//            }
//        var adres: Adres? = null
//        dbRef.document("LdIJ1hy6d7HOrGR8RiMS").get().await().let {
//            adres = it.toObject<Adres>()
//        }
//        return adres
//    }

//        var adres: Adres? = null
//        var docRef: DocumentReference = dbRef.document("LdIJ1hy6d7HOrGR8RiMS")
//        docRef.get().addOnSuccessListener { document ->
//            if (document != null) {
//                Log.d("succes", "DocumentSnapshot data: ${document.data}")
//                adres = document.toObject<Adres>()
//            } else {
//                Log.d("fail", "No such document")
//            }
//        }
//
//        return adres
//    }


//    private fun addAdresEventListener(adresReference: DatabaseReference) {
//        // [START adres_value_event_listener]
//        val adresListener = object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                // Get Adres object and use the values to update the UI
//                val Adres = dataSnapshot.getValue<Adres>()
//                // ...
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                // Getting Post failed, log a message
//                Log.w("failed", "loadPost:onCancelled", databaseError.toException())
//            }
//        }
//        adresReference.addValueEventListener(adresListener)
//        // [END post_value_event_listener]
//    }

//    fun get(id : String) : Adres {
//        val adresReference = dbRef.child("adres").child(adresId)
//        addAdresEventListener(adresReference)
//    }
//
//    // Promise-based API implementation
//    private var listener: ((Adres) -> Unit)? = null
//
//    fun setListener(replacement: (Adres) -> Unit) {
//        listener = replacement
//    }
//
//    fun removeListener() {
//        listener = null
//    }
//
//    fun call() {
//        listener?.let {
//            val adres = get()
//            it(adres)
//        }
//    }

}
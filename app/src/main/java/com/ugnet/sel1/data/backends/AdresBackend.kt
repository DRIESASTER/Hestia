package com.ugnet.sel1.data.backends

import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.ugnet.sel1.data.models.Adres


class AdresBackend {

    private lateinit var dbRef: DatabaseReference


    fun initializeDbRef() {
        // [START initialize_database_ref]
        dbRef = Firebase.database.reference.child("Adres")
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
        val key = dbRef.push().key
        if (key == null) {
            Log.w("error","Couldn't get push key for adres")
            return
        }

        val adres = Adres(straat, huisnummer, postcode, gemeente, land)
        val adresValues = adres.toMap()

        val childUpdates = hashMapOf<String, Any>(
            "/adres/$key" to adresValues
        )
        dbRef.updateChildren(childUpdates)
    }


    fun readAdres(adresId : String) : Adres? {
        val adresReference = dbRef.child("adres").child(adresId)
        var adres : Adres? = null
        adresReference.get().addOnSuccessListener {
            // Get Adres object and use the values to update the UI
            adres = it.getValue<Adres>()
            // ...
        }.addOnFailureListener {
            // Getting Post failed, log a message
            Log.w("failed", "loadPost:onCancelled", it)
        }
        return adres
//        addAdresEventListener(adresReference)
    }

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
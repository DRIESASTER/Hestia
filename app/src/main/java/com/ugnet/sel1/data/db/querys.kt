package com.ugnet.sel1.data.db

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase


abstract class querys {
    private lateinit var database: DatabaseReference

    fun initializeDbRef() {
        // [START initialize_database_ref]
        database = Firebase.database.reference
        // [END initialize_database_ref]
    }

    fun writeNewAdres(adresId : String, straat : String, huisnummer : Int, postcode : Int, gemeente : String, land : String) {
        val adres = Adres(straat, huisnummer, postcode, gemeente, land)
        database.child("adres").child(adresId).setValue(adres).addOnSuccessListener {
            // Write was successful!
            // ...
        }.addOnFailureListener {
            // Write failed
            // ...
        }
        // [END rtdb_write_new_user_task]
    }
    private fun addAdresEventListener(adresReference: DatabaseReference) {
        // [START adres_value_event_listener]
        val adresListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Adres object and use the values to update the UI
                val Adres = dataSnapshot.getValue<Adres>()
                // ...
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("failed", "loadPost:onCancelled", databaseError.toException())
            }
        }
        adresReference.addValueEventListener(adresListener)
        // [END post_value_event_listener]
    }

}
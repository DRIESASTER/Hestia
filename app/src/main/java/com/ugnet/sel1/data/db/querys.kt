package com.ugnet.sel1.data.db

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


abstract class querys {
    private lateinit var database: DatabaseReference

    fun initializeDbRef() {
        // [START initialize_database_ref]
        database = Firebase.database.reference
        // [END initialize_database_ref]
    }

    fun writeNewAdres(straat : String, huisnummer : Int, postcode : Int, gemeente : String, land : String) {
        val adres = Adres(straat, huisnummer, postcode, gemeente, land)
        database.child("adres").setValue(adres).addOnSuccessListener {
            // Write was successful!
            // ...
        }.addOnFailureListener {
            // Write failed
            // ...
        }
        // [END rtdb_write_new_user_task]
    }






}
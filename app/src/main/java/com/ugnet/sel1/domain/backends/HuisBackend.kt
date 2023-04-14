package com.ugnet.sel1.domain.backends

import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.ugnet.sel1.domain.models.Huis

class HuisBackend {

    private lateinit var dbRef: DatabaseReference

    fun initializeDbRef() {
        // [START initialize_database_ref]
        dbRef = Firebase.database.reference.child("Adres")
        // [END initialize_database_ref]
    }


    fun readHuis(huisId : String) : Huis? {
        val huisReference = dbRef.child("huizen").child(huisId)
        var huis : Huis? = null
        huisReference.get().addOnSuccessListener {
            // Get Adres object and use the values to update the UI
            huis = it.getValue<Huis>()
            // ...
        }.addOnFailureListener {
            // Getting Post failed, log a message
            Log.w("failed", "loadPost:onCancelled", it)
        }
        return huis
//        addAdresEventListener(adresReference)
    }

    fun readHuizen(huisIds : List<String>?) : List<Huis> {
        if (huisIds == null) {
            return emptyList()
        }
        var huizen: List<Huis> = ArrayList<Huis>()
        for (huisId in huisIds) {
            val huis = readHuis(huisId)
            if (huis != null) {
                huizen.plus(huis)
            }
        }
        return huizen
    }
}
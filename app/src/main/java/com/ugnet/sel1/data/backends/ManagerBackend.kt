package com.ugnet.sel1.data.backends

import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.ugnet.sel1.data.models.Manager

class ManagerBackend {

    private lateinit var dbRef: DatabaseReference


    fun initializeDbRef() {
        // [START initialize_database_ref]
        dbRef = Firebase.database.reference.child("Manager")
        // [END initialize_database_ref]
    }


    fun readManager(managerId : String) : Manager? {
        val managerReference = dbRef.child("manager").child(managerId)
        var manager : Manager? = null
        managerReference.get().addOnSuccessListener {
            // Get Adres object and use the values to update the UI
            manager = it.getValue<Manager>()
            // ...
        }.addOnFailureListener {
            // Getting Post failed, log a message
            Log.w("failed", "readManager:onCancelled", it)
        }
        return manager
//        addAdresEventListener(adresReference)
    }

}
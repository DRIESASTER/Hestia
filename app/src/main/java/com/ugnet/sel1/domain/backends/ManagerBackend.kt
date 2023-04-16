package com.ugnet.sel1.domain.backends

class ManagerBackend {

//    private lateinit var dbRef: DatabaseReference
//
//
//    fun initializeDbRef() {
//        // [START initialize_database_ref]
//        dbRef = Firebase.database.reference.child("Manager")
//        // [END initialize_database_ref]
//    }
//
//
//    fun readManager(managerId : String) : Manager? {
//        val managerReference = dbRef.child("manager").child(managerId)
//        var manager : Manager? = null
//        managerReference.get().addOnSuccessListener {
//            // Get Adres object and use the values to update the UI
//            manager = it.getValue<Manager>()
//            // ...
//        }.addOnFailureListener {
//            // Getting Post failed, log a message
//            Log.w("failed", "readManager:onCancelled", it)
//        }
//        return manager
////        addAdresEventListener(adresReference)
//    }

}
package com.ugnet.sel1.domain.models

import com.google.firebase.firestore.Exclude

data class Manager(val achternaam : String?, val voornaam : String?, val username : String?, val ownedHuizen : List<String>?, val ownedAppartementen : List<String>?) {
    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "achternaam" to achternaam,
            "voornaam" to voornaam,
            "username" to username,
            "ownedHuizen" to ownedHuizen,
            "ownedAppartementen" to ownedAppartementen
        )
    }



}
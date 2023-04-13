package com.ugnet.sel1.data.models

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class Adres(val straat : String?, val huisnummer : Int?, val postcode : Int?, val gemeente : String?, val land : String?) {
    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "straat" to straat,
            "huisnummer" to huisnummer,
            "postcode" to postcode,
            "gemeente" to gemeente,
            "land" to land
        )
    }
}
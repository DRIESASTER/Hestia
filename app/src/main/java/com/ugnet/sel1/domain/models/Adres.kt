package com.ugnet.sel1.domain.models

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.PropertyName

@IgnoreExtraProperties

data class Adres(
    var straat: String? = null,
    var huisnummer: Int? = null,
    var postcode: Int? = null,
    var gemeente: String? = null,
    var land: String? = null
)


//data class Adres(val straat : String, val huisnummer : Int, val postcode : Int, val gemeente : String, val land : String) {
//    // Null default values create a no-argument default constructor, which is needed
//    // for deserialization from a DataSnapshot.
//    @Exclude
//    fun toMap(): Map<String, Any?> {
//        return mapOf(
//            "gemeente" to gemeente,
//            "huisnummer" to huisnummer,
//            "land" to land,
//            "postcode" to postcode,
//            "straat" to straat
//        )
//    }
//}
package com.ugnet.sel1.data.db

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class Adres(val straat : String?, val huisnummer : Int?, val postcode : Int?, val gemeente : String?, val land : String?) {
    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.
}
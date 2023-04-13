package com.ugnet.sel1.data.models

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class Huis(val managerId : String?, val huurderId : String?, val adresId : String?) {
    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "managerId" to managerId,
            "huurderId" to huurderId,
            "adresId" to adresId
        )
    }
}
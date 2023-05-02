package com.ugnet.sel1.domain.models

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    var email : String? = null,
    var uid : String? = null,
    var accountType : String? = null,
    var voornaam : String? = null,
    var achternaam : String? = null
)
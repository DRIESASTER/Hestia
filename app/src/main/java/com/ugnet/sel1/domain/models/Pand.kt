package com.ugnet.sel1.domain.models

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.IgnoreExtraProperties
import com.ugnet.sel1.domain.repository.Kamers

@IgnoreExtraProperties

data class Pand(
    var straat : String? = null,
    var huisnummer : Int? = null,
    var postcode : Int? = null,
    var huurder : Huurder? = null,
    var isHuis : Boolean? = null,
    var kamers : List<DocumentReference>? = null,
    var id : String? = null,
)
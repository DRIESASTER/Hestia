package com.ugnet.sel1.domain.models.oudeShit

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
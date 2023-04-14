package com.ugnet.sel1.domain.models

import com.google.firebase.firestore.IgnoreExtraProperties
import com.ugnet.sel1.domain.repository.Kamers

@IgnoreExtraProperties

data class Pand(
    var adres : Adres? = null,
    var huurder : Huurder? = null,
    var isHuis : Boolean? = null,
    var kamers : Kamers? = null
)
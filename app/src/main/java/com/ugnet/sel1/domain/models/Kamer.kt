package com.ugnet.sel1.domain.models

import com.google.firebase.firestore.IgnoreExtraProperties
import com.ugnet.sel1.domain.repository.Issues

@IgnoreExtraProperties

data class Kamer(
    var huurder: Huurder? = null,
    var issues : Issues? = null,
    var naam : String? = null
)
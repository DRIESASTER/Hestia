package com.ugnet.sel1.domain.models

import com.google.firebase.firestore.IgnoreExtraProperties
import com.ugnet.sel1.domain.repository.Issues

@IgnoreExtraProperties

data class Kamer(
    var naam : String? = null,
    var huurder: String? = null,
    var issues : List<String>? = null,
)
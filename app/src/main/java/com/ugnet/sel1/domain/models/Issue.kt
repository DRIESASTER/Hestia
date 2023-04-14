package com.ugnet.sel1.domain.models

import com.google.firebase.firestore.IgnoreExtraProperties
import java.util.*

@IgnoreExtraProperties

data class Issue(
    var beschrijving: String? = null,
    var titel: String? = null,
    var datum: Date? = null,
)
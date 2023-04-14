package com.ugnet.sel1.domain.models

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties
import com.ugnet.sel1.domain.repository.Panden

@IgnoreExtraProperties

data class Manager(
    var username: String? = null,
    var voornaam: String? = null,
    var achternaam: String? = null,
    var ownedPanden: Panden? = null
)
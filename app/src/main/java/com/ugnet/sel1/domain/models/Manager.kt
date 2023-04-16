package com.ugnet.sel1.domain.models

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties
import com.ugnet.sel1.domain.repository.Panden
import com.ugnet.sel1.domain.useCases.panden.GetOwnedPanden

@IgnoreExtraProperties

data class Manager(
    var userId : String,
    var voornaam: String? = null,
    var achternaam: String? = null,
    var username: String? = null,
    var ownedPanden: List<String>? = null
)
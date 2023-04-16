package com.ugnet.sel1.domain.models.oudeShit

//import com.google.firebase.firestore.DocumentReference
//import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties
//import com.ugnet.sel1.domain.repository.Panden
//import com.ugnet.sel1.domain.useCases.oudeShit.panden.GetOwnedPanden

@IgnoreExtraProperties

data class Manager(
    var username: String? = null,
    var voornaam: String? = null,
    var achternaam: String? = null,
    var ownedPanden: List<String>? = null
)
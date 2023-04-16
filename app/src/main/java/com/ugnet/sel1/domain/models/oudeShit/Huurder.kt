package com.ugnet.sel1.domain.models.oudeShit

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties

data class Huurder(
    var userId : String,
    var voornaam: String,
    var achternaam: String,
    var username: String,
)
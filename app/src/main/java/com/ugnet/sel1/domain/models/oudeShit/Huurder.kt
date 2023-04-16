package com.ugnet.sel1.domain.models.oudeShit

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties

data class Huurder(
    var achternaam: String? = null,
    var voornaam: String? = null,
    var username: String? = null,
)
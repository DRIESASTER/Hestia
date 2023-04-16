package com.ugnet.sel1.domain.models

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties

data class Room(
    var naam: String? = null,
    var roomId: String? = null,
    var huurder: String? = null,
)
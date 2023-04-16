package com.ugnet.sel1.domain.models

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties

data class Property(
    var stad: String? = null,
    var isHuis: Boolean? = null,
    var straat: String? = null,
    var huisnummer: Int? = null,
    var postcode: Int? = null,
    var ownedBy: String? = null
)
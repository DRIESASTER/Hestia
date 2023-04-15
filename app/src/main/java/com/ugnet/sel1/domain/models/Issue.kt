package com.ugnet.sel1.domain.models

import com.google.firebase.Timestamp
import com.google.firebase.firestore.IgnoreExtraProperties
import java.util.*

@IgnoreExtraProperties

data class Issue(
    var beschrijving: String? = null,
    var titel: String? = null,
    var datum: Timestamp? = null,
    var status: Status? = null,
    var id: String? = null
)

enum class Status {
    notStarted,
    inProgress,
    finished
}
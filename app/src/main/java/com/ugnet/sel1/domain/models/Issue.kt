package com.ugnet.sel1.domain.models

import com.google.firebase.Timestamp
import com.google.firebase.firestore.IgnoreExtraProperties
import java.util.*

@IgnoreExtraProperties
data class Issue(
    var beschrijving: String? = null,
    var datum: Timestamp? = null,
    var titel: String? = null,
    var status: Status? = null,
    var roomId: String? = null,
    var issueId: String? = null,
    var issueType: IssueType? = null,
    var userId: String? = null,
)

enum class Status {
    notStarted,
    inProgress,
    finished
}

enum class IssueType {
    water,
    electricity,
    gas,
    other
}
package com.ugnet.sel1.ui.manager

import com.ugnet.sel1.domain.models.IssueType
import com.ugnet.sel1.domain.models.Status
import java.sql.Timestamp

data class IssueData(val title: String,
                     val description: String,
                     val id: String,
                     val status:Status,
                     val tenant:String,
                     val room:String,
                     val building: String,
                     val issuekind:IssueType,
                     val date : com.google.firebase.Timestamp
                     )

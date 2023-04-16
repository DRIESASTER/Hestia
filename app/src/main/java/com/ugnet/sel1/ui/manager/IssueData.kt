package com.ugnet.sel1.ui.manager

import com.ugnet.sel1.domain.models.Status

data class IssueData(val title: String, val description: String, val id: String, val status:Status, val tenant:String, val room:String)

package com.ugnet.sel1.domain.useCases


import com.ugnet.sel1.domain.models.Message
import com.ugnet.sel1.domain.repository.IssuesRepository

class SendMessagePerIssue constructor(
    private val repo: IssuesRepository
){
    operator fun invoke(propid: String, issueId : String, mes : Message) = repo.sendMessage(propid, issueId, mes)
}
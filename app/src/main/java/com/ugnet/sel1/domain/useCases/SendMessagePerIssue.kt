package com.ugnet.sel1.domain.useCases


import com.ugnet.sel1.domain.models.Message
import com.ugnet.sel1.domain.repository.IssuesRepository

class SendMessagePerIssuee constructor(
    private val repo: IssuesRepository
){
    operator fun invoke(issueId : String, mes : Message) = repo.sendMessage(issueId, mes)
}
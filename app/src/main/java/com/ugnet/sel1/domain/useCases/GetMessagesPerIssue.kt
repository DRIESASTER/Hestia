package com.ugnet.sel1.domain.useCases

import com.ugnet.sel1.domain.repository.IssuesRepository

class GetMessagesPerIssue constructor(
    private val repo: IssuesRepository
){
    operator fun invoke(issueId : String) = repo.getIssueMessages(issueId)
}

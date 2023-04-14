package com.ugnet.sel1.domain.useCases.Issues

import com.ugnet.sel1.domain.models.Status
import com.ugnet.sel1.domain.repository.IssuesRepository

class ChangeIssueStatus constructor(
    private val repo: IssuesRepository
) {

suspend operator fun invoke(
        issueId:String,
        status: Status
    ) = repo.changeIssueStatus(issueId, status)
}
package com.ugnet.sel1.domain.useCases.oudeShit.panden.Issues

import com.ugnet.sel1.domain.repository.IssuesRepository

class DeleteIssue constructor(
    private val repo: IssuesRepository
) {
    suspend operator fun invoke(
        issueId:String
    ) = repo.deleteIssueFromFirestore(issueId)
}
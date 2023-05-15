package com.ugnet.sel1.domain.useCases

import com.ugnet.sel1.domain.repository.IssuesRepository

class DeleteIssue constructor(
    private val repo: IssuesRepository
) {
    suspend operator fun invoke(propertyId:String, issueId:String) = repo.deleteIssueFromFirestore(propertyId, issueId)
}
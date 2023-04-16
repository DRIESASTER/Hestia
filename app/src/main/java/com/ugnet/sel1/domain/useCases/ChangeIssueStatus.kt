package com.ugnet.sel1.domain.useCases

import com.ugnet.sel1.domain.models.Status
import com.ugnet.sel1.domain.repository.IssuesRepository

class ChangeIssueStatus constructor(
    private val repo: IssuesRepository
) {
    suspend operator fun invoke(issueId:String, status: Status, propertyId:String) = repo.changeIssueStatusInFirestore(issueId, status, propertyId)
}
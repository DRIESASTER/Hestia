package com.ugnet.sel1.domain.useCases

import com.ugnet.sel1.domain.repository.IssuesRepository
import com.ugnet.sel1.domain.repository.RoomsRepository

class GetIssue constructor(
    private val repo: IssuesRepository
) {
    operator fun invoke(propertyId:String, issueId:String) = repo.getIssue(propertyId, issueId)
}
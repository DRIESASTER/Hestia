package com.ugnet.sel1.domain.useCases.Issues

import com.ugnet.sel1.domain.repository.AdresRepository
import com.ugnet.sel1.domain.repository.IssuesRepository


class GetIssue constructor(
    private val repo: IssuesRepository
) {
    operator fun invoke(id : String) = repo.getIssueFromFirestore(id)
}
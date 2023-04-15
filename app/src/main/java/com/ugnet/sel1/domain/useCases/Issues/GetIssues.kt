package com.ugnet.sel1.domain.useCases.Issues

import com.ugnet.sel1.domain.repository.IssuesRepository


class GetIssues constructor(
    private val repo: IssuesRepository
) {
    operator fun invoke(ids : List<String>) = repo.getIssuesFromFirestore(ids)
}
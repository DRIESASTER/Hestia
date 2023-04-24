package com.ugnet.sel1.domain.useCases

import com.ugnet.sel1.domain.repository.IssuesRepository

class GetIssuesPerProperty constructor(
    private val repo: IssuesRepository
) {
    operator fun invoke(propertyId:String) = repo.getIssuesPerPropertyFromFirestore(propertyId)
}
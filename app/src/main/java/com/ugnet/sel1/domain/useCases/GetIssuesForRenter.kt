package com.ugnet.sel1.domain.useCases

import com.ugnet.sel1.domain.repository.IssuesRepository

class GetIssuesForRenter constructor(
    private val repo: IssuesRepository
) {
    operator fun invoke(propertyId:String, userId:String) = repo.getIssuesForRenterFromFirestore(propertyId, userId)
}
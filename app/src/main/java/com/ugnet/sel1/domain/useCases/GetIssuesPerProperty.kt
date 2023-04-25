package com.ugnet.sel1.domain.useCases

import com.ugnet.sel1.domain.repository.IssuesRepository
import com.ugnet.sel1.domain.repository.IssuesResponse
import kotlinx.coroutines.flow.Flow

class GetIssuesPerProperty constructor(
    private val repo: IssuesRepository
) {
    operator fun invoke(propertyId:String): Flow<IssuesResponse> = repo.getIssuesPerPropertyFromFirestore(propertyId)
}
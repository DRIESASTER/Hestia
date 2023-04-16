package com.ugnet.sel1.domain.useCases

import com.ugnet.sel1.domain.repository.IssuesRepository


class GetIssuesForRoom constructor(
    private val repo: IssuesRepository
) {
    operator fun invoke(pandId:String, roomId:String) = repo.getIssuesByRoomFromFirestore(pandId, roomId)
}
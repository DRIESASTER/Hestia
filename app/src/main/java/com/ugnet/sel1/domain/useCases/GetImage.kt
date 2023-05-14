package com.ugnet.sel1.domain.useCases

import com.ugnet.sel1.domain.repository.IssuesRepository

class GetImage constructor(
    private val repo: IssuesRepository
) {
    operator fun invoke(imageUrl:String) = repo.getImage(imageUrl)
}
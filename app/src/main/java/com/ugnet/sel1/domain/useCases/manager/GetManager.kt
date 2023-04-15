package com.ugnet.sel1.domain.useCases.manager


import com.ugnet.sel1.domain.repository.ManagerRepository

class GetManager constructor(
    private val repo: ManagerRepository
) {
    operator fun invoke(id:String) = repo.getManagerFromFirestore(id)
}
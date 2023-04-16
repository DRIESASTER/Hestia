package com.ugnet.sel1.domain.useCases


import com.ugnet.sel1.domain.repository.UsersRepository

class GetUser constructor(
    private val repo: UsersRepository
) {
    operator fun invoke(id:String) = repo.getUserFromFirestore(id)
}
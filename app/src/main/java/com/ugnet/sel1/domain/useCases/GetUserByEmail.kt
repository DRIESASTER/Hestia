package com.ugnet.sel1.domain.useCases

import com.ugnet.sel1.domain.repository.UsersRepository

class GetUserByEmail constructor(
    private val repo: UsersRepository
) {
    operator fun invoke(email:String) = repo.getUserByEmail(email)
}
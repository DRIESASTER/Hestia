package com.ugnet.sel1.domain.useCases

import com.ugnet.sel1.domain.models.Manager
import com.ugnet.sel1.data.repositories.HuisRepo
import javax.inject.Inject

class GetOwnedHuizenUseCase @Inject constructor(
    private val repository: HuisRepo
) {
    fun invoke(manager : Manager) = repository.getHuizen(manager.ownedHuizen)
}
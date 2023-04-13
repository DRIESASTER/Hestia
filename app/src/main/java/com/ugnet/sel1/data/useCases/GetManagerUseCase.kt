package com.ugnet.sel1.data.useCases

import com.ugnet.sel1.data.models.Manager
import com.ugnet.sel1.data.repositories.ManagerRepo
import javax.inject.Inject

class GetManagerUseCase @Inject constructor(
    private val repository: ManagerRepo
) {
    fun invoke(id : String) : Manager = repository.getManager(id)
}
package com.ugnet.sel1.data.useCases

import com.ugnet.sel1.data.repositories.AdresRepo
import javax.inject.Inject

class GetAdresUseCase @Inject constructor(
    private val repository: AdresRepo
) {
    fun invoke(id : String) = repository.getAdres(id)
}
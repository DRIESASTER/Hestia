package com.ugnet.sel1.data.repositories

import javax.inject.Inject

class GetAdresUseCase @Inject constructor(
    private val repository: AdresRepo
) {
    fun invoke(id : String) = repository.getAdres(id)
}
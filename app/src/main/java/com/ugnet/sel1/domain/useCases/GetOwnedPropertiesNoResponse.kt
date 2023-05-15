package com.ugnet.sel1.domain.useCases

import com.ugnet.sel1.domain.repository.PropertiesRepository

class GetOwnedPropertiesNoResponse constructor(
    private val repo: PropertiesRepository
) {
    operator fun invoke() = repo.getOwnedPropertiesNoResponse()
}
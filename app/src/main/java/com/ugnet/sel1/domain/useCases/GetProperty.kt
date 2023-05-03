package com.ugnet.sel1.domain.useCases

import com.ugnet.sel1.domain.repository.PropertiesRepository

class GetProperty constructor(
    private val repo: PropertiesRepository
) {
    operator fun invoke(id:String) = repo.getProperty(id)
}
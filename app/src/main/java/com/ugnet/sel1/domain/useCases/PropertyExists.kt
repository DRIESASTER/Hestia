package com.ugnet.sel1.domain.useCases

import com.ugnet.sel1.authentication.selection.AuthRepository
import com.ugnet.sel1.data.repositories.GetUserRepository
import com.ugnet.sel1.domain.repository.PropertiesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class PropertyExists @Inject constructor(private val repo: PropertiesRepository) {
    suspend operator fun invoke(id: String): Boolean {
        return repo.propertyExists(id)
    }
}
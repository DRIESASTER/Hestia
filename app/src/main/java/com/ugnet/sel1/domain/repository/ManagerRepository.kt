package com.ugnet.sel1.domain.repository

import com.ugnet.sel1.domain.models.Manager
import com.ugnet.sel1.domain.models.Response
import kotlinx.coroutines.flow.Flow

typealias ManagerResponse = Response<Manager?>

interface ManagerRepository {

    fun getManagerFromFirestore(id: String): Flow<ManagerResponse>

}
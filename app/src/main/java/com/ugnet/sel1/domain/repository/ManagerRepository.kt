package com.ugnet.sel1.domain.repository

import com.ugnet.sel1.domain.models.Kamer
import com.ugnet.sel1.domain.models.Manager
import com.ugnet.sel1.domain.models.Response

typealias Managers = List<Manager>
typealias ManagersResponse = Response<Manager>
//typealias AddBookResponse = Response<Boolean>
//typealias DeleteBookResponse = Response<Boolean>

interface ManagerRepository {

//    fun getAdresesFromFirestore(): Flow<AdresesResponse>

//    suspend fun addBookToFirestore(title: String, author: String): AddBookResponse
//
//    suspend fun deleteBookFromFirestore(bookId: String): DeleteBookResponse
}
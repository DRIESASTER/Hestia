package com.ugnet.sel1.domain.repository

import com.ugnet.sel1.domain.models.Huurder
import com.ugnet.sel1.domain.models.Response
import kotlinx.coroutines.flow.Flow

typealias Huurders = List<Huurder>
typealias HuurdersResponse = Response<Huurders>
//typealias AddBookResponse = Response<Boolean>
//typealias DeleteBookResponse = Response<Boolean>

interface HuurderRepository {

//    fun getAdresesFromFirestore(): Flow<AdresesResponse>

//    suspend fun addBookToFirestore(title: String, author: String): AddBookResponse
//
//    suspend fun deleteBookFromFirestore(bookId: String): DeleteBookResponse
}
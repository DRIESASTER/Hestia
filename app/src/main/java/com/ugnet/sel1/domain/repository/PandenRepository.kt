package com.ugnet.sel1.domain.repository

import com.ugnet.sel1.domain.models.Pand
import com.ugnet.sel1.domain.models.Response

typealias Panden = List<Pand>
typealias PandenResponse = Response<Panden>
//typealias AddBookResponse = Response<Boolean>
//typealias DeleteBookResponse = Response<Boolean>

interface PandenRepository {

//    fun getAdresesFromFirestore(): Flow<AdresesResponse>

//    suspend fun addBookToFirestore(title: String, author: String): AddBookResponse
//
//    suspend fun deleteBookFromFirestore(bookId: String): DeleteBookResponse
}
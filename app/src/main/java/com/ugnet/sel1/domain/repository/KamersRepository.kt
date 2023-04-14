package com.ugnet.sel1.domain.repository


import com.ugnet.sel1.domain.models.Kamer
import com.ugnet.sel1.domain.models.Response
import kotlinx.coroutines.flow.Flow


typealias Kamers = List<Kamer>
typealias KamersResponse = Response<Kamers>
//typealias AddBookResponse = Response<Boolean>
//typealias DeleteBookResponse = Response<Boolean>

interface KamersRepository {

//    fun getAdresesFromFirestore(): Flow<AdresesResponse>

//    suspend fun addBookToFirestore(title: String, author: String): AddBookResponse
//
//    suspend fun deleteBookFromFirestore(bookId: String): DeleteBookResponse
}
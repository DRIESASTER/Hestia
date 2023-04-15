package com.ugnet.sel1.domain.repository

import com.google.firebase.firestore.DocumentReference
import com.ugnet.sel1.domain.models.Pand
import com.ugnet.sel1.domain.models.Response
import kotlinx.coroutines.flow.Flow

typealias Panden = List<Pand>
typealias PandenResponse = Response<Panden>
typealias PandResponse = Response<Pand?>

//typealias AddBookResponse = Response<Boolean>
//typealias DeleteBookResponse = Response<Boolean>

interface PandenRepository {

    fun getPandFromFirestore(id: String): Flow<PandResponse>

    fun getPandenFromFirestore(): Flow<PandenResponse>

//    fun getAdresesFromFirestore(): Flow<AdresesResponse>

//    suspend fun addBookToFirestore(title: String, author: String): AddBookResponse
//
//    suspend fun deleteBookFromFirestore(bookId: String): DeleteBookResponse
}
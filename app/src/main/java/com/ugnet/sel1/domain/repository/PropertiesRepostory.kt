package com.ugnet.sel1.domain.repository

import com.ugnet.sel1.domain.models.Property
import com.ugnet.sel1.domain.models.Response
import kotlinx.coroutines.flow.Flow

typealias Properties = List<Property>
typealias PropertiesResponse = Response<Properties>
typealias PandResponse = Response<Property?>
typealias DeletePandResponse = Response<Boolean>

//typealias AddBookResponse = Response<Boolean>
//typealias DeleteBookResponse = Response<Boolean>

interface PropertiesRepository {

//    fun getPandFromFirestore(id: String): Flow<PandResponse>
    fun getOwnedPropertiesFromFirestore(userId:String): Flow<PropertiesResponse>
//    suspend fun deletePandFromFirestore(id: String): DeletePandResponse

}

//    fun getAdresesFromFirestore(): Flow<AdresesResponse>

//    suspend fun addBookToFirestore(title: String, author: String): AddBookResponse
//
//    suspend fun deleteBookFromFirestore(bookId: String): DeleteBookResponse

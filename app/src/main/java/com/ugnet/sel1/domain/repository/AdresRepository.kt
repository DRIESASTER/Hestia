package com.ugnet.sel1.domain.repository

import com.ugnet.sel1.domain.models.Adres
import com.ugnet.sel1.domain.models.Response
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.flow.Flow

typealias Adreses = List<Adres>
typealias AdresesResponse = Response<Adreses>
typealias AddAdresResponse = Response<Boolean>
typealias DeleteAdresResponse = Response<Boolean>
//typealias AddBookResponse = Response<Boolean>
//typealias DeleteBookResponse = Response<Boolean>

interface AdresRepository {

    fun getAdresesFromFirestore(): Flow<AdresesResponse>

    suspend fun addAdrestoFirestore(straat:String, huisnummer:Int, gemeente:String, land:String, postcode:Int): AddAdresResponse

    suspend fun deleteAdresFromFirestore(adresId: String): DeleteAdresResponse

//    suspend fun addBookToFirestore(title: String, author: String): AddBookResponse
//
//    suspend fun deleteBookFromFirestore(bookId: String): DeleteBookResponse
}
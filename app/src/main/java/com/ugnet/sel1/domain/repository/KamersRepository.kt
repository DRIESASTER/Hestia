package com.ugnet.sel1.domain.repository


import com.ugnet.sel1.domain.models.Kamer
import com.ugnet.sel1.domain.models.Response
import kotlinx.coroutines.flow.Flow


typealias Kamers = List<Kamer>
typealias KamersResponse = Response<Kamers>
typealias KamerResponse = Response<Kamer?>
typealias AddKamerResponse = Response<Boolean>
typealias DeleteKamerResponse = Response<Boolean>
typealias EditKamerResponse = Response<Boolean>

//typealias AddBookResponse = Response<Boolean>
//typealias DeleteBookResponse = Response<Boolean>

interface KamersRepository {

    fun getKamerFromFirestore(kamerId: String): Flow<KamerResponse>

    fun getKamersFromFirestore(): Flow<KamersResponse>

    suspend fun addKamerToFirestore(huurder: String, naam: String): AddKamerResponse

    suspend fun deleteKamerFromFirestore(kamerId: String): DeleteKamerResponse

    suspend fun editKamerFromFirestore(kamerId: String, huurder: String, naam: String, issues: List<String>): EditKamerResponse

}
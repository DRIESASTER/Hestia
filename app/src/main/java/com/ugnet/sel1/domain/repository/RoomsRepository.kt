package com.ugnet.sel1.domain.repository


import com.ugnet.sel1.domain.models.Room
import com.ugnet.sel1.domain.models.Response
import kotlinx.coroutines.flow.Flow


typealias Rooms = List<Room>
typealias RoomsResponse = Response<Rooms>
//typealias KamerResponse = Response<Room?>
//typealias AddKamerResponse = Response<Boolean>
//typealias DeleteKamerResponse = Response<Boolean>
//typealias EditKamerResponse = Response<Boolean>

//typealias AddBookResponse = Response<Boolean>
//typealias DeleteBookResponse = Response<Boolean>

interface RoomsRepository {


    fun getRoomsForPropertyFromFirestore(pandId: String): Flow<RoomsResponse>

//    fun getKamerFromFirestore(kamerId: String): Flow<KamerResponse>
//
//    fun getKamersFromFirestore(): Flow<KamersResponse>

//    suspend fun addKamerToFirestore(huurder: String, naam: String): AddKamerResponse
//
//    suspend fun deleteKamerFromFirestore(kamerId: String): DeleteKamerResponse
//
//    suspend fun editKamerFromFirestore(kamerId: String, huurder: String, naam: String, issues: List<String>): EditKamerResponse

}
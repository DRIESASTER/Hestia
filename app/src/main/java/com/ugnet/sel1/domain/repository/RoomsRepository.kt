package com.ugnet.sel1.domain.repository


import com.ugnet.sel1.domain.models.Property
import com.ugnet.sel1.domain.models.Room
import com.ugnet.sel1.domain.models.Response
import kotlinx.coroutines.flow.Flow


typealias Rooms = List<Room>
typealias RoomsResponse = Response<Rooms>
typealias AddRoomResponse = Response<Boolean>
typealias DeleteRoomResponse = Response<Boolean>
//typealias KamerResponse = Response<Room?>
//typealias AddKamerResponse = Response<Boolean>
//typealias DeleteKamerResponse = Response<Boolean>
//typealias EditKamerResponse = Response<Boolean>

//typealias AddBookResponse = Response<Boolean>
//typealias DeleteBookResponse = Response<Boolean>

interface RoomsRepository {


    fun getRoomsForPropertyFromFirestore(pandId: String): Flow<RoomsResponse>

    fun getAccesibleRoomsByUserInFirestore(user: String, propertyId:String): Flow<RoomsResponse>

    suspend fun addRoomToPropertyInFirestore(pandId: String, naam: String, huurderLijst:List<String>): AddRoomResponse

    suspend fun deleteRoomFromPropertyInFirestore(pandId: String, roomId: String): DeleteRoomResponse

    suspend fun addUserToRoom(propertyId:String, roomId: String, userId: String): AddRoomResponse

}

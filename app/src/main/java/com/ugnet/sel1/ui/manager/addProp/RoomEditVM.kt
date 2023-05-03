package com.ugnet.sel1.ui.manager.addProp

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.domain.repository.DeleteRoomResponse
import com.ugnet.sel1.domain.repository.RoomsResponse
import com.ugnet.sel1.domain.repository.UsersResponse
import com.ugnet.sel1.domain.useCases.UseCases
import com.ugnet.sel1.ui.components.RoomData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomEditVM @Inject constructor(
    private val useCases: UseCases,
    @PropId private val propId: String,
    @Email private val email: String
    ): ViewModel(){



    init{
        Log.d("roomeditVm", propId + email)
    }

    var removeRoomsResponse by mutableStateOf<DeleteRoomResponse>(Response.Loading)
        private set

    var roomsResponse by mutableStateOf<RoomsResponse>(Response.Loading)
        private set

    fun getUserbyMail(email:String): Flow<UsersResponse> = useCases.getUserByEmail(email)

    fun addrenter(propid:String,tenant: String) = viewModelScope.launch {
        useCases.addUserToProperty(tenant,propid)
    }

    fun deleterenter(propid:String,tenant: String) = viewModelScope.launch {
        useCases.removeUserFromProperty(tenant,propid)
    }

    fun getRoomsForProperty(propertyId: String): Flow<RoomsResponse> = useCases.getRoomsForProperty(propertyId)

    fun processRooms() : MutableList<RoomData> {
        return mutableListOf()
    }

    fun deleteRoomFromProperty(propid:String,roomId: String) = viewModelScope.launch {
        removeRoomsResponse = Response.Loading
        removeRoomsResponse = useCases.deleteRoomFromProperty(propid, roomId)
    }

    fun getRentinglist (propid:String): Flow<UsersResponse> = useCases.getRentersList(propid)

    fun addroom(propid:String,roomName: String, tenantName: String) = viewModelScope.launch {
        useCases.addRoomToProperty(propid, roomName, listOf(tenantName))

        Log.d("RoomEditVM", "addroom: $propid $roomName $tenantName")
    }

}

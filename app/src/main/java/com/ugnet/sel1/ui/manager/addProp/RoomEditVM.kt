package com.ugnet.sel1.ui.manager.addProp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.domain.repository.DeleteRoomResponse
import com.ugnet.sel1.domain.useCases.UseCases
import com.ugnet.sel1.ui.components.RoomData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomEditVM @Inject constructor(private val useCases: UseCases): ViewModel(){

    var propid : String by mutableStateOf("")
    var removeRoomsResponse by mutableStateOf<DeleteRoomResponse>(Response.Loading)
        private set

    fun processRooms() : MutableList<RoomData> {
        return mutableListOf()
    }


    fun deleteRoomFromProperty(roomId: String) = viewModelScope.launch {
        removeRoomsResponse = Response.Loading
        removeRoomsResponse = useCases.deleteRoomFromProperty(propid, roomId)
    }


    fun addroom(roomName: String, tenantName: String) = viewModelScope.launch {
        useCases.addRoomToProperty(propid,roomName, tenantName)
    }

}

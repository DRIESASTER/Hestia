package com.ugnet.sel1.ui.manager.addProp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.domain.repository.AddPropertyResponse
import com.ugnet.sel1.domain.repository.UserResponse
import com.ugnet.sel1.domain.useCases.UseCases
import com.ugnet.sel1.ui.components.RoomData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPropVM @Inject constructor(private val useCases: UseCases): ViewModel() {

    var tenant : String by mutableStateOf("")
    var city : String by mutableStateOf("")
    var street : String by mutableStateOf("")
    var number : String by mutableStateOf("")
    var postalCode : String by mutableStateOf("")
    var userResponse by mutableStateOf<UserResponse>(Response.Loading)
        private set
    var rooms : MutableList<RoomData> = mutableListOf()
    var isHouse : Boolean by mutableStateOf(false)

    var addPropertyResponse by mutableStateOf<AddPropertyResponse>(Response.Loading)
        private set
    init{
        getUser(Firebase.auth.currentUser?.uid.toString())
    }

    fun getUser(id: String) = viewModelScope.launch {
        useCases.getUser(id).collect { response ->
            userResponse = response
        }
    }

    fun changeState(){
        isHouse = !isHouse
    }

    fun removeRoom(naam:String): (String) -> Unit {
        rooms.filter{ it.roomName != naam }
        return {}
    }

    //TODO: upload room to db
    fun saveProp(managerID:String) = viewModelScope.launch{
        addProperty(number.toInt(), isHouse, managerID, postalCode.toInt(), city, street)
        when(val addPropResponse = addPropertyResponse){
            is Response.Success -> {
                for(room in rooms){
                    useCases.addRoomToProperty(addPropResponse.data,room.roomName, room.tenantName)
                }
            }
            else -> {}
        } //            useCases.addRoomToProperty(addPropertyResponse.data?.,room.roomName, room.tenantName)
    }

    fun addProperty(huisnummer:Int, isHuis:Boolean, ownedBy:String, postcode:Int, stad:String, straat:String) = viewModelScope.launch {
        useCases.addProperty(huisnummer, isHuis, ownedBy, postcode, stad, straat).collect { response ->
            addPropertyResponse = response
        }
    }

    fun addRoom(roomname:String, tenantname:String){
        rooms.add(RoomData(roomname,tenantname))
    }
}
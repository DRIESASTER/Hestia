package com.ugnet.sel1.ui.manager.addProp

import android.util.Log
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
    var saveClicked : Boolean by mutableStateOf(false)
    var tenant : String by mutableStateOf("")
    var city : String by mutableStateOf("")
    var street : String by mutableStateOf("")
    var number : String by mutableStateOf("")
    var postalCode : String by mutableStateOf("")
    var userResponse by mutableStateOf<UserResponse>(Response.Loading)
        private set
    var rooms : MutableList<RoomData> = mutableListOf()
    var isHouse : Boolean by mutableStateOf(false)
    var editing : Boolean by mutableStateOf(false)
    var addPropertyResponse by mutableStateOf<AddPropertyResponse>(Response.Loading)
        private set


//    var deleteRoomFromPropertyResponse by mutableStateOf<DeleteRoomResponse>(Response.Success(false))
//        private set

    init{
//        if(propid != null){
//            editing = true
//        }
        getUser(Firebase.auth.currentUser?.email.toString())

    }

    fun getUser(id: String) = viewModelScope.launch {
        useCases.getUser(id).collect { response ->
            userResponse = response
        }
    }

    fun changeState(){
        isHouse = !isHouse
    }

    fun removeRoom(naam:String)= viewModelScope.launch{
        rooms = rooms.filter{ it.roomName != naam }.toMutableList()
    }

    //TODO: upload room to db
    fun saveProp(managerID:String) = viewModelScope.launch {
        Log.d("SAVE PROP manager email", managerID)
        addPropertyResponse = Response.Loading
        addPropertyResponse = useCases.addProperty(huisnummer = number.toInt(),
            type = if (isHouse) "Huis" else "Appartement",
            ownedBy = managerID,
            postcode = postalCode.toInt(),
            stad = city,
            straat = street)
        Log.d("ADDPROP", addPropertyResponse.toString())
    }

//    fun addProperty(huisnummer:Int, type:String, ownedBy:String, postcode:Int, stad:String, straat:String) = viewModelScope.launch {
//        addPropertyResponse = Response.Loading
//        addPropertyResponse = useCases.addProperty(huisnummer, type, ownedBy, postcode, stad, straat)
//    }

    //not necessary anymore because of extra screen
    fun addRoom(roomname:String, tenantname:String)= viewModelScope.launch{
        rooms.add(RoomData(roomname,tenantname))
    }



}
package com.ugnet.sel1.ui.manager.addProp

import android.util.Log
import androidx.compose.material.Text
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


//    var deleteRoomFromPropertyResponse by mutableStateOf<DeleteRoomResponse>(Response.Success(false))
//        private set

    init{
        getUser(Firebase.auth.currentUser?.uid.toString())
//        getUser("Fti1aAWM1USFFCJg2I7LFniWrlT2")
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
        Log.d("TAG", "saveProp: $managerID")
        addProperty(number.toInt(), if (isHouse) "Huis" else "Appartement", managerID, postalCode.toInt(), city, street)
        when (addPropertyResponse) {
            is Response.Success -> {
                Log.d("TAG","YES HET WERKT")
//                rooms.forEach {
//                    useCases.addRoomToProperty(addPropertyResponse.data!!, it.roomName, it.tenantName)
//                }
            }
            else -> {
                //niks
            }
        }
    } //            useCases.addRoomToProperty(addPropertyResponse.data?.,room.roomName, room.tenantName)

    fun addProperty(huisnummer:Int, type:String, ownedBy:String, postcode:Int, stad:String, straat:String) = viewModelScope.launch {
        addPropertyResponse = Response.Loading
        addPropertyResponse = useCases.addProperty(huisnummer, type, ownedBy, postcode, stad, straat)
        Log.d("ADDPROP", addPropertyResponse.toString())
    }
//    fun addProperty(huisnummer:Int, type:String, ownedBy:String, postcode:Int, stad:String, straat:String) = viewModelScope.launch {
//        addPropertyResponse = Response.Loading
//        addPropertyResponse = useCases.addProperty(huisnummer, type, ownedBy, postcode, stad, straat)
//    }

    fun addRoom(roomname:String, tenantname:String){
        rooms.add(RoomData(roomname,tenantname))
    }
}
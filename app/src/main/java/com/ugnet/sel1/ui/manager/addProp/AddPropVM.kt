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
import com.ugnet.sel1.domain.repository.PropertyResponse
import com.ugnet.sel1.domain.repository.UserResponse
import com.ugnet.sel1.domain.useCases.UseCases
import com.ugnet.sel1.ui.components.RoomData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPropVM @Inject constructor(
    private val useCases: UseCases,
    @PropId private val propId: String
    ): ViewModel() {

    init{
        Log.d("AddPropVM", propId)
    }

    var saveClicked : Boolean by mutableStateOf(false)
    var tenant : String by mutableStateOf("")
    var city : String by mutableStateOf("")
    var street : String by mutableStateOf("")
    var number : String by mutableStateOf("")
    var postalCode : String by mutableStateOf("")
    var userResponse by mutableStateOf<UserResponse>(Response.Loading)
        private set
    var rooms : MutableList<RoomData> = mutableListOf()
    var huurlijst : MutableList<String> = mutableListOf()
    var isHouse : Boolean by mutableStateOf(false)
    var addPropertyResponse by mutableStateOf<AddPropertyResponse>(Response.Loading)
        private set
    var updatePropertyResponse by mutableStateOf<Response<Boolean>>(Response.Loading)
        private set
    var propid : String by mutableStateOf("")
//    var deleteRoomFromPropertyResponse by mutableStateOf<DeleteRoomResponse>(Response.Success(false))
//        private set

    init{
        propid = propId
        Log.d("AddPropVM", propid)
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

    fun patchProp(managerID: String) = viewModelScope.launch {
        updatePropertyResponse = Response.Loading
        updatePropertyResponse = useCases.editProperty(propid, huisnummer = number.toInt(),
            type = if (isHouse) "Huis" else "Appartement",
            postcode = postalCode.toInt(),
            stad = city,
            straat = street, ownedBy = managerID, huurdersLijst = if(isHouse) listOf(tenant) else huurlijst)
    }

    fun getProperty(propid: String): Flow<PropertyResponse> = useCases.getProperty(propid)

}
package com.ugnet.sel1.ui.manager.addProp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.ugnet.sel1.domain.useCases.UseCases
import com.ugnet.sel1.ui.components.RoomData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddPropVM @Inject constructor(private val useCases :UseCases): ViewModel() {

    var tenant : String by mutableStateOf("")
    var city : String by mutableStateOf("")
    var street : String by mutableStateOf("")
    var number : String by mutableStateOf("")
    var postalCode : String by mutableStateOf("")

    var rooms : MutableList<RoomData> = mutableListOf()
    var isHouse : Boolean by mutableStateOf(false)
        private set
    init{}

    fun changeState(){
        isHouse = !isHouse
    }

    fun removeRoom(naam:String): (String) -> Unit {
        rooms.filter{ it.roomName != naam }
        return {}
    }

    //TODO: upload room to db
    fun saveRoom(){

    }

    fun addRoom(roomname:String, tenantname:String){
        rooms.add(RoomData(roomname,tenantname))
    }
}
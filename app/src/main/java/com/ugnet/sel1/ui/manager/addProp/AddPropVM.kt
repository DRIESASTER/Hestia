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
open class AddPropVM @Inject constructor(): ViewModel() {

    open var tenant : String by mutableStateOf("")
    open var city : String by mutableStateOf("")
    open var street : String by mutableStateOf("")
    open var number : String by mutableStateOf("")
    open var postalCode : String by mutableStateOf("")

    var rooms : MutableList<RoomData> = mutableListOf()
    open var isHouse : Boolean by mutableStateOf(false)

    init{}

    open fun changeState(){
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
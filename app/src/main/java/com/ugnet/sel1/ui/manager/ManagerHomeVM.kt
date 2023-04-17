package com.ugnet.sel1.ui.manager

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.domain.repository.IssuesResponse
import com.ugnet.sel1.domain.repository.PropertiesResponse
import com.ugnet.sel1.domain.repository.RoomsResponse
import com.ugnet.sel1.domain.useCases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


//add issues later
@HiltViewModel
class ManagerHomeVM @Inject constructor(private val useCases:UseCases) : ViewModel() {
    //issues for a specific room
    var issuesForRoomResponse by mutableStateOf<IssuesResponse>(Response.Loading)
        private set
    //prperties owned by user
    var ownedPropertiesResponse by mutableStateOf<PropertiesResponse>(Response.Loading)
        private set
    //rooms in a specific property
    var roomsForPropertyResponse by mutableStateOf<RoomsResponse>(Response.Loading)
        private set


    init{
//        getProperties()
    }


    fun getOwnedProperties(id: String) = viewModelScope.launch {
        useCases.getOwnedProperties(id).collect { response ->
            ownedPropertiesResponse = response
        }
    }

    fun getIssuesForRoom(pandId: String, roomId: String) = viewModelScope.launch {
        useCases.getIssuesForRoom(pandId, roomId).collect { response ->
            issuesForRoomResponse = response
        }
    }

    fun getRoomsForProperty(propertyId: String) = viewModelScope.launch {
        useCases.getRoomsForProperty(propertyId).collect { response ->
            roomsForPropertyResponse = response
        }
    }


//    val uid: String? = Firebase.auth.currentUser?.uid
    //FIXME: proces db response
//    private fun getIssues(issues:List<String>) = viewModelScope.launch {
//            useCases.getIssues(issues).collect { response ->
//                IssueResponse = response
//            }
//    }

//    FIXME: proces db response
//    private fun getProperties() = viewModelScope.launch {
//        if(uid != null){
//            useCases.getPanden().collect { response ->
//                PropertyResponse = response
//            }
//        }

    }

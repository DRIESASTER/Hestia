package com.ugnet.sel1.ui.resident

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.domain.repository.IssuesResponse
import com.ugnet.sel1.domain.repository.RoomsResponse
import com.ugnet.sel1.domain.repository.UserResponse
import com.ugnet.sel1.domain.useCases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResidentHomeVM @Inject constructor(private val useCases: UseCases): ViewModel(){
    var currentState by mutableStateOf(true)

    var userResponse by mutableStateOf<UserResponse>(Response.Loading)
        private set

    var allIssuesResponse by mutableStateOf<IssuesResponse>(Response.Loading)
        private set

    var rentedRoomsResponse by mutableStateOf<RoomsResponse>(Response.Loading)
        private set

    var allIssuesForRoom by mutableStateOf<IssuesResponse>(Response.Loading)

    var allRoomsResponse by mutableStateOf<RoomsResponse>(Response.Loading)

    init{
        getUser(Firebase.auth.currentUser?.uid.toString())
//        getUser("Fti1aAWM1USFFCJg2I7LFniWrlT2")
    }

    fun getUser(id: String) = viewModelScope.launch {
        useCases.getUser(id).collect { response ->
            userResponse = response
        }
    }

    fun getRooms() = viewModelScope.launch {
        useCases.getRentedRoomsByUser(Firebase.auth.currentUser?.uid.toString()).collect { response ->
            allRoomsResponse = response
        }
    }

    fun getIssuesForRenter(propertyId: String) = viewModelScope.launch {
        useCases.getIssuesForRenter(propertyId, Firebase.auth.currentUser?.uid.toString()).collect { response ->
            allIssuesForRoom = response
        }
    }

    fun getRentedRoomsByUser(userId: String) = viewModelScope.launch {
        useCases.getAccesibleRoomsPerUser(userId).collect { response ->
            rentedRoomsResponse = response
        }
    }

//    fun getIssuesForRenterByPand(propertyId:String, userId: String) = viewModelScope.launch {
//        useCases.getIssuesForRenter(userId).collect { response ->
//            issuesForRenterResponse = response
//        }
//    }







}
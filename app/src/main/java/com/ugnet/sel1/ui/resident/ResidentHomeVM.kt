package com.ugnet.sel1.ui.resident

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ugnet.sel1.domain.models.Issue
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.domain.repository.IssuesResponse
import com.ugnet.sel1.domain.repository.PropertiesResponse
import com.ugnet.sel1.domain.repository.RoomsResponse
import com.ugnet.sel1.domain.repository.UserResponse
import com.ugnet.sel1.domain.useCases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
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
        private set

    var allRoomsResponse by mutableStateOf<RoomsResponse>(Response.Loading)
        private set

    var allRentedPropertiesResponse by mutableStateOf<PropertiesResponse>(Response.Loading)

    init{
        getRentedProperties()
//        getUser("Fti1aAWM1USFFCJg2I7LFniWrlT2")
    }


    fun getRentedProperties() = viewModelScope.launch {
        useCases.getRentedProperties(Firebase.auth.currentUser?.email.toString()).collect { response ->
            allRentedPropertiesResponse = response
        }
    }

    fun getAccesibleRoomsForProperty(propertyId: String): Flow<RoomsResponse> = useCases.getAccesibleRoomsPerUser(Firebase.auth.currentUser?.email.toString(), propertyId)

    fun getUser(userid: String): Flow<UserResponse> = useCases.getUser(userid)

    fun getCurrentUser(): Flow<UserResponse> = useCases.getUser(Firebase.auth.currentUser?.email.toString())

    fun getIssuesForRoom(property: String, room: String): Flow<IssuesResponse> = useCases.getIssuesForRoom(property, room)


//    fun getRentedRoomsByUser(userId: String) = viewModelScope.launch {
//        useCases.getAccesibleRoomsPerUser(userId).collect { response ->
//            rentedRoomsResponse = response
//        }
//    }

    fun getIssuesForRenter(propertyId: String): Flow<IssuesResponse> = useCases.getIssuesForRenter(propertyId, Firebase.auth.currentUser?.email.toString())

//    fun getIssuesForRenterByPand(propertyId:String, userId: String) = viewModelScope.launch {
//        useCases.getIssuesForRenter(userId).collect { response ->
//            issuesForRenterResponse = response
//        }
//    }


    
}
package com.ugnet.sel1.ui.manager

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.domain.models.Status
import com.ugnet.sel1.domain.repository.*
import com.ugnet.sel1.domain.useCases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


//add issues later
@HiltViewModel
class ManagerHomeVM @Inject constructor(private val useCases:UseCases) : ViewModel() {
    var currentState by mutableStateOf(true)

        var roomsForPropertyResponse by mutableStateOf<RoomsResponse>(Response.Loading)
        private set


    private val _ownedProperties = MutableStateFlow<PropertiesResponse>(Response.Loading)
    val ownedProperties: StateFlow<PropertiesResponse> = _ownedProperties.asStateFlow()

    var ownedPropertiesResponse by mutableStateOf<PropertiesResponse>(Response.Loading)
        private set

    var issuesForBuildingResponse by mutableStateOf<IssuesResponse>(Response.Loading)
        private set

//    var issuesForManagerResponse by mutableStateOf<Response<MutableList<IssueData>>>(Response.Loading)
//        private set
    //issues for a specific room
    var issuesForRoomResponse by mutableStateOf<IssuesResponse>(Response.Loading)
        private set
    //prperties owned by user
//    var ownedPropertiesResponse by mutableStateOf<PropertiesResponse>(Response.Loading)
//        private set
    //rooms in a specific property

    var IssueStatusResponse by mutableStateOf<ChangeIssueStatusResponse>(Response.Loading)
        private set

//    var formattedPropertiesResponse by mutableStateOf<Response<MutableList<PropertyData>>>(Response.Loading)
//        private set

//    var formatPropertyResponse by mutableStateOf<Response<PropertyData>>(Response.Loading)
//        private set


    init {
        Log.d("MANAGERVIEWMODEL INIT", "INTIALIZE")
        getOwnedProperties(Firebase.auth.currentUser?.uid.toString())
//        getIssuesForManager()
    }


    fun getOwnedProperties(id: String) = viewModelScope.launch {
        useCases.getOwnedProperties(id).collect() {response ->
            ownedPropertiesResponse = response
        }
    }

    fun getRoomsPerProperty(propertyId: String): Flow<RoomsResponse> = useCases.getRoomsForProperty(propertyId)


    fun getIssuesPerProperty(propertyId: String): Flow<IssuesResponse> = useCases.getIssuesPerProperty(propertyId)

    fun getUser(userid: String): Flow<UserResponse> = useCases.getUser(userid)


    fun changeIssueStatus(issueId: String, status: Status, propertyId: String) = viewModelScope.launch {
        IssueStatusResponse = Response.Loading
        IssueStatusResponse = useCases.changeIssueStatus(issueId, status, propertyId)
    }

    fun removeProperty(propertyId: String) = viewModelScope.launch {
        useCases.deleteProperty(propertyId)
    }
    fun getIssuesForRoom(pandId: String, roomId: String) = viewModelScope.launch {
        useCases.getIssuesForRoom(pandId, roomId).collect { response ->
            issuesForRoomResponse = response
        }
    }}


//    private fun processIssues(issues: List<Issue>): MutableList<IssueData> {
//        var issuesData = mutableListOf<IssueData>()
//        for(issue in issues){
//            //var user = getUser(issue.userId!!)
//            Log.d("ManagerHomeScreen","adding issue ${issue.issueId} to list")
//            issuesData.add(IssueData(id = issue.issueId!!, room = issue.roomId!!,
//                description = issue.beschrijving!!, status = issue.status!!, date = issue.datum!!, tenant = ""/*user.voornaam + " " + user.achternaam*/, issuekind = issue.issueType!!, title = issue.titel!!))
//        }
//        return issuesData
//    }


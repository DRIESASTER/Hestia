package com.ugnet.sel1.ui.manager

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ugnet.sel1.domain.models.*
import com.ugnet.sel1.domain.repository.*
import com.ugnet.sel1.domain.useCases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


//add issues later
@HiltViewModel
class ManagerHomeVM @Inject constructor(private val useCases:UseCases) : ViewModel() {
    var currentState by mutableStateOf(true)

    var roomsForPropertyResponse by mutableStateOf<RoomsResponse>(Response.Loading)
        private set

    var ownedPropertiesResponseFormatted by mutableStateOf<Response<MutableList<PropertyData>>>(Response.Loading)
        private set

    var issuesForBuildingResponse by mutableStateOf<Response<MutableList<IssueData>>>(Response.Loading)
        private set

    var issuesForManagerResponse by mutableStateOf<Response<MutableList<IssueData>>>(Response.Loading)
        private set
    //issues for a specific room
    var issuesForRoomResponse by mutableStateOf<IssuesResponse>(Response.Loading)
        private set
    //prperties owned by user
    var ownedPropertiesResponse by mutableStateOf<PropertiesResponse>(Response.Loading)
        private set
    //rooms in a specific property

    var IssueStatusResponse by mutableStateOf<ChangeIssueStatusResponse>(Response.Loading)
        private set


    init{
        //getOwnedProperties(Firebase.auth.currentUser?.uid.toString())
        getOwnedProperties("Fti1aAWM1USFFCJg2I7LFniWrlT2")
        getIssuesForManager()
    }


    fun getOwnedProperties(id: String) = viewModelScope.launch {
        useCases.getOwnedProperties(id).collect { response ->
            ownedPropertiesResponse = response
            when(response){
                is Response.Success -> {
                    Log.d("ManagerHomeVM", "getOwnedProperties: ${response.data}")
                    ownedPropertiesResponseFormatted = formatPropertyData(response.data)
                }
                else -> {}
            }
        }
    }

    private fun formatPropertyData(data: List<Property>): Response<MutableList<PropertyData>> {
        var properties = mutableListOf<PropertyData>()
        for(property in data){
            getRoomsForProperty(property.propertyId.toString())
            getAllIssuesForProperty(property.propertyId.toString())
            properties.add(PropertyData(propertyId = property.propertyId!!, name = property.straat + " " + property.huisnummer
                , address = property.straat + " " + property.huisnummer + ", " + property.postcode + " " + property.stad,
                tenants = (when(val roomsResponse = roomsForPropertyResponse){
                    is Response.Success -> {
                        roomsResponse.data
                    }
                    else -> {
                        mutableListOf()
                    }
                }).size,issues = (when(val issuesResponse = issuesForBuildingResponse){
                    is Response.Success -> {
                        issuesResponse.data
                    }
                    else -> {
                        mutableListOf()
                    }
                }).size))
                }
        return Response.Success(properties)
    }

    fun changeIssueStatus(issueId: String, status: Status, propertyId: String) = viewModelScope.launch {
        IssueStatusResponse = Response.Loading
        IssueStatusResponse = useCases.changeIssueStatus(issueId, status, propertyId)
    }

    fun getRoomsForProperty(propertyId: String) = viewModelScope.launch {
        useCases.getRoomsForProperty(propertyId).collect { response ->
            roomsForPropertyResponse = response
        }
    }

    fun getIssuesForRoom(pandId: String, roomId: String) = viewModelScope.launch {
        useCases.getIssuesForRoom(pandId, roomId).collect { response ->
            issuesForRoomResponse = response
        }
    }

    fun getIssuesForManager(){
        var issues = mutableListOf<IssueData>()
        when(val propertiesResponse = ownedPropertiesResponse){
            is Response.Success -> {
                for(property in propertiesResponse.data){
                    getAllIssuesForProperty(property.propertyId!!)
                    when(val issuesResponse = issuesForBuildingResponse){
                        is Response.Success -> {
                            for(issue in issuesResponse.data){
                                issues.add(issue)
                            }

                        }
                        else -> {}
                    }
                }
                Log.d("ManagerHomeVM", "getIssuesForManager succes: ${issues.size}")
                issuesForManagerResponse = Response.Success(issues)
            }
            else -> {}
        }

    }

    fun getAllIssuesForProperty(propertyId: String) = viewModelScope.launch {
        var issues = mutableListOf<IssueData>()
        getRoomsForProperty(propertyId)
        when (val roomresponse = roomsForPropertyResponse) {
            is Response.Success -> {
                for (room in roomresponse.data) {
                    getIssuesForRoom(propertyId, room.roomId!!)
                    when (val issuesResponse = issuesForRoomResponse) {
                        is Response.Success -> {
                            for (issue in issuesResponse.data) {
                                issues.add(processIssueData(issue,room,propertyId))
                            }
                        }
                        else -> {}
                    }
                }
            }
            else -> {}
        }
        issuesForBuildingResponse = Response.Success(issues)
    }

    private fun processIssueData(issue: Issue, room: Room, propertyid: String): IssueData {
        var user = getUser(room.huurderId!!)
        return IssueData(
            title = issue.titel!!,
            id = issue.issueId!!,
            description = issue.beschrijving!!,
            status =issue.status!!,
            date = issue.datum!!,
            room=room.naam!!,
            tenant = user.voornaam!! + " " + user.achternaam!!,
            building =propertyid,
            issuekind = issue.issueType!!,
        )
    }

    private fun getUser(huurderId: String): User {
        var user = User()
        viewModelScope.launch {
            useCases.getUser(huurderId).collect { response ->
                when (response) {
                    is Response.Success -> {
                        user = response.data!!
                    }
                    else -> {}
                }
            }
        }
        return user
    }

}

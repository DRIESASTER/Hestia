package com.ugnet.sel1.presentation.adres

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.domain.models.Status
import com.ugnet.sel1.domain.repository.*
import com.ugnet.sel1.domain.useCases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdresTestVM @Inject constructor(private val useCases : UseCases): ViewModel() {


      var issuesForRoomResponse by mutableStateOf<IssuesResponse>(Response.Loading)
            private set

      var userResponse by mutableStateOf<UserResponse>(Response.Loading)
            private set

      var ownedPropertiesResponse by mutableStateOf<PropertiesResponse>(Response.Loading)
            private set

      var roomsForPropertyResponse by mutableStateOf<RoomsResponse>(Response.Loading)
            private set

      var changeIssueStatusResponse by mutableStateOf<ChangeIssueStatusResponse>(Response.Success(false))
            private set

      var addRoomToPropertyResponse by mutableStateOf<AddRoomResponse>(Response.Success(false))
            private set

      var deleteRoomFromPropertyResponse by mutableStateOf<DeleteRoomResponse>(Response.Success(false))
            private set

      var addPandResponse by mutableStateOf<AddPandResponse>(Response.Success(false))
            private set


      init {
            getUser("4YNpPq1e3Gg2FTrnqPoW")
            getOwnedProperties("4YNpPq1e3Gg2FTrnqPoW")
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

      fun getUser(id: String) = viewModelScope.launch {
            useCases.getUser(id).collect { response ->
                  userResponse = response
            }
      }

      fun getRoomsForProperty(propertyId: String) = viewModelScope.launch {
            useCases.getRoomsForProperty(propertyId).collect { response ->
                  roomsForPropertyResponse = response
            }
      }


      fun changeIssueStatus(issueId: String, status: Status, propertyId: String) = viewModelScope.launch {
            changeIssueStatusResponse = Response.Loading
            changeIssueStatusResponse = useCases.changeIssueStatus(issueId, status, propertyId)
      }

      fun addRoomToProperty(propertyId: String, naam:String, huurder:String?) = viewModelScope.launch {
            addRoomToPropertyResponse = Response.Loading
            addRoomToPropertyResponse = useCases.addRoomToProperty(propertyId, naam, huurder)
      }

      fun deleteRoomFromProperty(propertyId: String, roomId: String) = viewModelScope.launch {
            deleteRoomFromPropertyResponse = Response.Loading
            deleteRoomFromPropertyResponse = useCases.deleteRoomFromProperty(propertyId, roomId)
      }




//      fun deletePand(id: String) = viewModelScope.launch {
//            deletePandResponse = Response.Loading
//            deletePandResponse = useCases.deletePand(id)
//      }
}
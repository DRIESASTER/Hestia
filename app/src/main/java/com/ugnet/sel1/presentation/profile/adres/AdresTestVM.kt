package com.ugnet.sel1.presentation.profile.adres

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ugnet.sel1.domain.models.IssueType
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

      var rentedRoomsResponse by mutableStateOf<RoomsResponse>(Response.Loading)
            private set

      var changeIssueStatusResponse by mutableStateOf<ChangeIssueStatusResponse>(Response.Success(false))
            private set

      var addRoomToPropertyResponse by mutableStateOf<AddRoomResponse>(Response.Success(false))
            private set

      var deleteRoomFromPropertyResponse by mutableStateOf<DeleteRoomResponse>(Response.Success(false))
            private set

      var addPropertyResponse by mutableStateOf<AddPropertyResponse>(Response.Loading)
            private set

      var deletePropertyResponse by mutableStateOf<DeletePropertyResponse>(Response.Success(false))
            private set

      var userByEmailResponse by mutableStateOf<UsersResponse>(Response.Loading)
            private set

      var addIssueResponse by mutableStateOf<AddIssueResponse>(Response.Loading)
            private set



      init {
//            getUser("4YNpPq1e3Gg2FTrnqPoW")
//            getOwnedProperties("4YNpPq1e3Gg2FTrnqPoW")
//            changeIssueStatus("kUXh7T1OH6CDyuYOuEEJ", Status.inProgress, "QTx6rzIOf8Y5G1KQQPUB")
            getIssuesForRoom("QTx6rzIOf8Y5G1KQQPUB","2gLemNlYgYghm7InAZW3")
      }


      fun getUserByEmail(email: String) = viewModelScope.launch {
            useCases.getUserByEmail(email).collect { response ->
                  userByEmailResponse = response
            }
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

//      fun getRoomsForProperty(propertyId: String) = viewModelScope.launch {
//            useCases.getRoomsForProperty(propertyId).collect { response ->
//                  roomsForPropertyResponse = response
//            }
//      }


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

//      fun addProperty(huisnummer:Int, type:String, ownedBy:String, postcode:Int, stad:String, straat:String) = viewModelScope.launch {
//            useCases.addProperty(huisnummer, type, ownedBy, postcode, stad, straat).collect { response ->
//                  addPropertyResponse = response
//            }
//      }

      fun addIssue(beschrijving:String, titel:String, propertyId: String, roomId:String, issueType: IssueType) = viewModelScope.launch {
            addIssueResponse = Response.Loading
            addIssueResponse = useCases.addIssue(beschrijving, titel, propertyId, roomId, issueType)
      }

      fun deleteProperty(propertyId: String) = viewModelScope.launch {
            deletePropertyResponse = Response.Loading
            deletePropertyResponse = useCases.deleteProperty(propertyId)
      }

      
/*
     fun getRentedRoomsByUser(userId: String) = viewModelScope.launch {
            useCases.getAccesibleRoomsPerUser(userId).collect { response ->
=======
     fun getRentedRoomsByUser(userId: String,propertyId: String) = viewModelScope.launch {
            useCases.getAccesibleRoomsPerUser(userId,propertyId).collect { response ->
>>>>>>> 53039e9146c98956ee56914c42967a1f44842c08
                  rentedRoomsResponse = response
            }
      }
*/

}
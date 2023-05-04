package com.ugnet.sel1.ui.resident

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ugnet.sel1.authentication.selection.AuthRepository
import com.ugnet.sel1.domain.models.IssueType
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.domain.repository.*
import com.ugnet.sel1.domain.useCases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResidentHomeVM @Inject constructor(
    private val useCases: UseCases,
    private val authRepo : AuthRepository
    ): ViewModel(){

    var allRentedPropertiesResponse by mutableStateOf<PropertiesResponse>(Response.Loading)

    init{
        getRentedProperties()
    }


    fun getRentedProperties() = viewModelScope.launch {
        useCases.getRentedProperties(Firebase.auth.currentUser?.email.toString()).collect { response ->
            allRentedPropertiesResponse = response
        }
    }

    fun signOut() = authRepo.signOut();

    fun getAccesibleRoomsForProperty(propertyId: String): Flow<RoomsResponse> = useCases.getAccesibleRoomsPerUser(Firebase.auth.currentUser?.email.toString(), propertyId)

    fun getUser(userid: String): Flow<UserResponse> = useCases.getUser(userid)

    fun getCurrentUser(): Flow<UserResponse> = useCases.getUser(Firebase.auth.currentUser?.email.toString())

    fun getIssuesForRoom(property: String, room: String): Flow<IssuesResponse> = useCases.getIssuesForRoom(property, room)

    fun addIssue(description:String, title:String, property:String, room:String, type: IssueType) = viewModelScope.launch {
        useCases.addIssue(description, title, property, room, type, Firebase.auth.currentUser?.email.toString())
    }
}
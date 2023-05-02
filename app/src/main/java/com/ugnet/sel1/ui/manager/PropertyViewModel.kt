package com.ugnet.sel1.ui.manager

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.domain.repository.IssuesResponse
import com.ugnet.sel1.domain.repository.RoomsResponse
import com.ugnet.sel1.domain.useCases.UseCases
import kotlinx.coroutines.launch
import javax.inject.Inject

class PropertyViewModel @Inject constructor(private val useCases: UseCases) : ViewModel() {
    var currentState by mutableStateOf(true)


    var roomsForPropertyResponse by mutableStateOf<RoomsResponse>(Response.Loading)
        private set


    var issuesForBuildingResponse by mutableStateOf<IssuesResponse>(Response.Loading)
        private set


//    fun getRoomsForProperty(propertyId: String) = viewModelScope.launch {
//        useCases.getRoomsForProperty(propertyId).collect { response ->
//            roomsForPropertyResponse = response
//        }
//    }

//    fun getAllIssuesForProperty(propertyId: String) = viewModelScope.launch {
//        useCases.getIssuesPerProperty(propertyId).collect { response ->
//            issuesForBuildingResponse = response
//        }
//    };

}
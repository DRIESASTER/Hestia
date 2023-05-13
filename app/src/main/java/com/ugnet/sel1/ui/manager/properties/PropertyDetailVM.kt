package com.ugnet.sel1.ui.manager.properties

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ugnet.sel1.domain.models.Property
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.domain.repository.IssuesResponse
import com.ugnet.sel1.domain.repository.RoomsResponse
import com.ugnet.sel1.domain.useCases.UseCases
import com.ugnet.sel1.ui.manager.issues.PropId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PropertyDetailVM @Inject constructor(
    private val useCases: UseCases,
    @PropId private val propId: String
) : ViewModel() {

    private val propertyData: Flow<Response<Property?>> = useCases.getProperty(propId)

    val uiState: StateFlow<Response<Property?>> = propertyData.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        Response.Loading
    )

    fun getIssues(roomId: String): Flow<IssuesResponse> {
        return useCases.getIssuesForRoom(propId,roomId = roomId)
    }

    fun getRooms(): Flow<RoomsResponse> = useCases.getRoomsForProperty(propId)

}








package com.ugnet.sel1.ui.announcements

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ugnet.sel1.domain.models.Announcement
import com.ugnet.sel1.domain.models.Property
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.domain.repository.PropertiesResponse
import com.ugnet.sel1.domain.useCases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnnouncementsViewModel @Inject constructor(private val useCases: UseCases) : ViewModel() {

    val propertiesData: Flow<PropertiesResponse> = useCases.getOwnedProperties()

    fun getAnnouncements(properties : List<Property>) = viewModelScope.launch {

    }

    fun addAnnouncement(propertyId: String, announcement: String) = viewModelScope.launch {
        useCases.addAnnouncementToProperty(propertyId, announcement = announcement)
    }
}
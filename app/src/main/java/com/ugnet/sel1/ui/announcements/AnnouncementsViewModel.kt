package com.ugnet.sel1.ui.announcements

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ugnet.sel1.common.snackbar.SnackbarManager
import com.ugnet.sel1.domain.models.*
import com.ugnet.sel1.domain.repository.Properties
import com.ugnet.sel1.domain.repository.PropertiesResponse
import com.ugnet.sel1.domain.repository.UserResponse
import com.ugnet.sel1.domain.useCases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.ugnet.sel1.R.string as AppText
@HiltViewModel
class AnnouncementsViewModel @Inject constructor(private val useCases: UseCases) : ViewModel() {

    private val propertiesData: Flow<List<Property>> = useCases.getOwnedPropertiesNoResponse()

    private  val currentUser : Flow<User> = useCases.getUserNoResponse()

    private val announcements: Flow<List<Announcement>> = currentUser.flatMapLatest { user ->
        when (user.accountType) {
            "Manager" -> useCases.getAnnouncementsForManager()
            "Huurder"-> useCases.getAnnouncementsForHiree()
            else -> throw IllegalStateException("Unknown role: ${user.accountType}")
        }
    }


    var uiState: StateFlow<AnnouncementUiState> = combine(
        currentUser,
        announcements,
        propertiesData
    ) { user, ann, properties ->
        AnnouncementUiState.Success(user, ann, properties)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, AnnouncementUiState.Loading)



    fun addAnnouncement(propertyId: String, announcement: String) = viewModelScope.launch {
        if(!useCases.propertyExist(propertyId)){
            SnackbarManager.showMessage(AppText.property_doesnt_exist)
        } else if(announcement.isBlank()){
            SnackbarManager.showMessage(AppText.announcement_error)
        } else {
            useCases.addAnnouncementToProperty(propertyId, announcement = announcement)
        }
    }
}
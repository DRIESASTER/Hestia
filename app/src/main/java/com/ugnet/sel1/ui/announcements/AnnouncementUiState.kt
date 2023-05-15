package com.ugnet.sel1.ui.announcements

import com.ugnet.sel1.domain.models.Announcement
import com.ugnet.sel1.domain.models.Property
import com.ugnet.sel1.domain.models.User

sealed interface AnnouncementUiState {

    object Loading : AnnouncementUiState

    data class Success(
        val currentUser : User,
        val announcements : List<Announcement>,
        val ownedProperties : List<Property>
        ) : AnnouncementUiState
}
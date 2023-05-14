package com.ugnet.sel1.domain.useCases

import com.ugnet.sel1.domain.models.Announcement
import com.ugnet.sel1.domain.models.IssueType
import com.ugnet.sel1.domain.repository.IssuesRepository
import com.ugnet.sel1.domain.repository.PropertiesRepository

class AddAnnouncementToProperty constructor(
    private val repo : PropertiesRepository
){
    suspend operator fun invoke(propId : String, announcement: String) = repo.addAnnouncement(propId, announcement)
}


package com.ugnet.sel1.domain.useCases

import android.net.Uri
import com.ugnet.sel1.domain.models.IssueType
import com.ugnet.sel1.domain.repository.IssuesRepository

class AddIssue constructor(
    private val repo: IssuesRepository
) {
    suspend operator fun invoke(beschrijving:String, titel:String, propertyId:String, roomId:String, issueType:IssueType, userId:String, imageUri: Uri?) = repo.addIssueToFirestore(beschrijving, titel, propertyId, roomId, issueType, userId, imageUri)
}
package com.ugnet.sel1.domain.repository

import android.net.Uri
import com.ugnet.sel1.domain.models.*
//import com.ugnet.sel1.domain.useCases.oudeShit.panden.Issues.ChangeIssueStatus
import kotlinx.coroutines.flow.Flow

typealias Issues = List<Issue>
typealias IssueResponse = Response<Issue?>
typealias IssuesResponse = Response<Issues>
typealias ChangeIssueStatusResponse = Response<Boolean>
typealias AddIssueResponse = Response<String>
typealias DeleteIssueResponse = Response<Boolean>
interface IssuesRepository {

    fun getIssuesByRoomFromFirestore(pandId: String, roomId: String): Flow<IssuesResponse>

    suspend fun addIssueToFirestore(
        beschrijving: String,
        titel: String,
        propertyId: String,
        roomId: String,
        issueType: IssueType,
        userId: String,
        imageUri: Uri?
    ): AddIssueResponse

    //
    fun getIssuesForRenterFromFirestore(propertyId: String, userId: String): Flow<IssuesResponse>
    fun getIssuesPerPropertyFromFirestore(propertyId: String): Flow<IssuesResponse>
    suspend fun deleteIssueFromFirestore(issueId: String): DeleteIssueResponse

    fun getIssue(propertyId: String, issueId: String): Flow<IssueResponse>

    suspend fun changeIssueStatusInFirestore(
        propertyId: String,
        status: Status,
        issueId: String
    ): ChangeIssueStatusResponse


    fun sendMessage(propid: String, issueId: String, message: Message)
    fun getIssueMessages(propId: String, issueId: String): Flow<Response<List<Message>>>

    fun getImage(imageUrl: String): Flow<Response<ByteArray>>
}

package com.ugnet.sel1.domain.repository

import com.google.firebase.Timestamp
import com.ugnet.sel1.domain.models.Issue
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.domain.models.Status
import com.ugnet.sel1.domain.useCases.oudeShit.panden.Issues.ChangeIssueStatus
import kotlinx.coroutines.flow.Flow

typealias Issues = List<Issue>
typealias IssuesResponse = Response<Issues>
typealias ChangeIssueStatusResponse = Response<Boolean>
//typealias AddBookResponse = Response<Boolean>
//typealias DeleteBookResponse = Response<Boolean>

interface IssuesRepository {

    fun getIssuesByRoomFromFirestore(pandId:String, roomId:String): Flow<IssuesResponse>

//    suspend fun addIssueToFirestore(beschrijving: String, datum: Timestamp, titel: String): AddIssueResponse
//
//    suspend fun deleteIssueFromFirestore(issueId: String): DeleteIssueResponse
//
//    suspend fun changeIssueStatus(issueId: String, status: Status): ChangeIssueStatusResponse

//
//    fun getAdresesFromFirestore(): Flow<AdresesResponse>

//    suspend fun addBookToFirestore(title: String, author: String): AddBookResponse
//
//    suspend fun deleteBookFromFirestore(bookId: String): DeleteBookResponse
    suspend fun changeIssueStatusInFirestore(propertyId: String, status: Status, issueId: String) : ChangeIssueStatusResponse
}
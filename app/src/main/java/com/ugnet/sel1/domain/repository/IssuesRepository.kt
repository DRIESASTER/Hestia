package com.ugnet.sel1.domain.repository

import com.google.firebase.Timestamp
import com.ugnet.sel1.domain.models.Issue
import com.ugnet.sel1.domain.models.Response
import kotlinx.coroutines.flow.Flow

typealias Issues = List<Issue>
typealias IssuesResponse = Response<Issues>
typealias IssueResponse = Response<Issue?>
typealias AddIssueResponse = Response<Boolean>
typealias DeleteIssueResponse = Response<Boolean>
//typealias AddBookResponse = Response<Boolean>
//typealias DeleteBookResponse = Response<Boolean>

interface IssuesRepository {

    fun getIssueFromFirestore(id: String): Flow<IssueResponse>

    suspend fun addIssueToFirestore(beschrijving: String, datum: Timestamp, titel: String): AddIssueResponse

    suspend fun deleteIssueFromFirestore(issueId: String): DeleteIssueResponse
//
//    fun getAdresesFromFirestore(): Flow<AdresesResponse>

//    suspend fun addBookToFirestore(title: String, author: String): AddBookResponse
//
//    suspend fun deleteBookFromFirestore(bookId: String): DeleteBookResponse
}
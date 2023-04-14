package com.ugnet.sel1.domain.repository

import com.ugnet.sel1.domain.models.Issue
import com.ugnet.sel1.domain.models.Response
import kotlinx.coroutines.flow.Flow

typealias Issues = List<Issue>
typealias IssuesResponse = Response<Issues>
//typealias AddBookResponse = Response<Boolean>
//typealias DeleteBookResponse = Response<Boolean>

interface IssuesRepository {
//
//    fun getAdresesFromFirestore(): Flow<AdresesResponse>

//    suspend fun addBookToFirestore(title: String, author: String): AddBookResponse
//
//    suspend fun deleteBookFromFirestore(bookId: String): DeleteBookResponse
}
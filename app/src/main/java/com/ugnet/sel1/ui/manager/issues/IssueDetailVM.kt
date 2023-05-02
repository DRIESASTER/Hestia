package com.ugnet.sel1.ui.manager.issues

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.domain.repository.IssueResponse
import com.ugnet.sel1.domain.repository.IssuesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IssueDetailVM @Inject constructor(
    private val repo: IssuesRepository,
    @IssueId private val issueId: String,
    @PropId private val propId: String
): ViewModel() {

    var issueDataResponse by mutableStateOf<IssueResponse>(Response.Loading)
        private set


    init {
        getIssue(propId, issueId)
    }

    fun getIssue(propId: String, issueId: String) = viewModelScope.launch {
        repo.getIssue(propId, issueId).collect{response ->
            Log.d("getIssue", response.toString())
            issueDataResponse = response
        }
    }

    public val issueTest = issueId
    public val propTest = propId



}
package com.ugnet.sel1.ui.chat

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ugnet.sel1.authentication.selection.AuthRepository
import com.ugnet.sel1.domain.models.Issue
import com.ugnet.sel1.domain.models.Message
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.domain.repository.IssuesRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChatViewModel @Inject constructor(
    private val repo: IssuesRepository,
): ViewModel() {

    private val _messages = MutableStateFlow<Response<List<Message>>>(Response.Loading)
    val messages: MutableStateFlow<Response<List<Message>>> = _messages

    public val _issue = MutableStateFlow<Issue?>(null)

    fun setIssue(issue: Issue) {
        _issue.value = issue
        getIssueMessages(issue.issueId!!)

    }

    private fun getIssueMessages(issueId: String) {
        viewModelScope.launch {
            repo.getIssueMessages(issueId).collect { messages ->
                _messages.value = messages
            }
        }
    }

    fun sendMessage(issueId: String, message: Message) {
        repo.sendMessage(issueId, message)
    }
}
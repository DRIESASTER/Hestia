package com.ugnet.sel1.ui.manager.issues

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ugnet.sel1.domain.models.Message
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.domain.models.Status
import com.ugnet.sel1.domain.repository.ChangeIssueStatusResponse
import com.ugnet.sel1.domain.repository.IssueResponse
import com.ugnet.sel1.domain.repository.IssuesRepository
import com.ugnet.sel1.domain.repository.UserResponse
import com.ugnet.sel1.domain.useCases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IssueDetailVM @Inject constructor(
    private val repo: IssuesRepository,
    private val useCases: UseCases,
    @IssueId private val issueId: String,
    @PropId private val propId: String
) : ViewModel() {

    var issueDataResponse by mutableStateOf<IssueResponse>(Response.Loading)
    var IssueStatusResponse by mutableStateOf<ChangeIssueStatusResponse>(Response.Loading)
        private set


    private val _messages = MutableLiveData<Response<List<Message>>>()
    val messages: LiveData<Response<List<Message>>> get() = _messages

    init {
        getIssue(propId, issueId)
        getIssueMessages(propId, issueId)
    }

    fun getIssue(propId: String, issueId: String) = viewModelScope.launch {
        repo.getIssue(propId, issueId).collect { response ->
            Log.d("getIssue", response.toString())
            issueDataResponse = response
        }
    }

    private fun getIssueMessages(propId: String, issueId: String) {
        viewModelScope.launch {
            repo.getIssueMessages(propId, issueId).collect { messages ->
                _messages.value = messages
            }
        }
    }


    fun sendMessage(messageText: String)  = viewModelScope.launch {
        val mes = Message(Firebase.auth.currentUser!!.email!!, messageText)
        repo.sendMessage(propId, issueId, mes)
    }

    fun changeIssueStatus(issueId: String, status: Status) = viewModelScope.launch {
        IssueStatusResponse = Response.Loading
        IssueStatusResponse = useCases.changeIssueStatus(issueId, status, propId)
    }

    fun getRoom() {
        TODO("Not yet implemented")
    }

    //fun getRoom(roomid:String):Flow<RoomResponse> = useCases.getRoom(roomid)

    fun getUser(userid:String): Flow<UserResponse> = useCases.getUser(userid)
}
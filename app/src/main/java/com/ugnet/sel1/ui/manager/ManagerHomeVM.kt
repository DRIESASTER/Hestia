package com.ugnet.sel1.ui.manager

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.domain.repository.IssuesResponse
import com.ugnet.sel1.domain.repository.PandenResponse
import com.ugnet.sel1.domain.useCases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


//add issues later
@HiltViewModel
class ManagerHomeVM @Inject constructor(private val useCases:UseCases) : ViewModel() {


    //TODO: fix with issuedata
    var IssueResponse by mutableStateOf<IssuesResponse>(Response.Loading)
        private set

    var PropertyResponse by mutableStateOf<PandenResponse>(Response.Loading)
        private set

    init{
        getProperties()
    }

    val uid: String? = Firebase.auth.currentUser?.uid
    //FIXME: proces db response
    private fun getIssues(issues:List<String>) = viewModelScope.launch {
            useCases.getIssues(issues).collect { response ->
                IssueResponse = response
            }
    }

    //FIXME: proces db response
    private fun getProperties() = viewModelScope.launch {
        if(uid != null){
            useCases.getPanden().collect { response ->
                PropertyResponse = response
            }
        }

    }




}

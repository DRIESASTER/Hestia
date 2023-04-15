package com.ugnet.sel1.ui.manager

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.ugnet.sel1.domain.repository.IssueResponse
import com.ugnet.sel1.domain.useCases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


//add issues later
@HiltViewModel
class ManagerHomeVM @Inject constructor(private val useCases:UseCases) : ViewModel() {
    //TODO: fix with issuedata
    var issues by mutableStateOf<List<IssueResponse>>(emptyList())
        private set


    var properties by mutableStateOf<List<PropertyData>>(emptyList())
        private set

    var selectedView by mutableStateOf(true)

    //FIXME: add user id
//    private fun getIssues() = viewModelScope.launch {
//        useCases.getIssue("help").collect { response ->
//            issues = listOf(response)
//        }
//    }

    //FIXME: add user id
//    private fun getProperties() = viewModelScope.launch {
//        useCases.getOwnedPanden().collect { response:PropertyData ->
//            properties = listOf(response)
//        }
//    }


}

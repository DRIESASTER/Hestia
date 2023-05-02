package com.ugnet.sel1.ui.manager.issues

import androidx.lifecycle.ViewModel
import com.ugnet.sel1.domain.repository.IssuesRepository
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class IssueDetailVM @Inject constructor(
    private val repo: IssuesRepository,
): ViewModel() {



}
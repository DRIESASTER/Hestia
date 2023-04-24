package com.ugnet.sel1.ui.resident

import androidx.lifecycle.ViewModel
import com.ugnet.sel1.domain.useCases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddIssueVM @Inject constructor(private val useCases: UseCases): ViewModel(){
}
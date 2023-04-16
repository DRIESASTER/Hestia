package com.ugnet.sel1.authentication.selection

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RoleSelectionViewModel @Inject constructor() : ViewModel() {
    val selectedRole = mutableStateOf("")
}

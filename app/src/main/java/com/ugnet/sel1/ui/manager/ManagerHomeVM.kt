package com.ugnet.sel1.ui.manager;


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ugnet.sel1.data.useCases.GetAdresUseCase
import com.ugnet.sel1.data.useCases.GetOwnedHuizenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

////add issues later
//@HiltViewModel
//class ManagerHomeVM @Inject constructor(private val ownedHuizenUseCase: GetOwnedHuizenUseCase ,private val adresUseCase:GetAdresUseCase) : ViewModel() {
//    var buildings = MutableLiveData<List<BuildingData>>()
//
//
//}

data class BuildingData(val name: String, val address: String, val id: String)

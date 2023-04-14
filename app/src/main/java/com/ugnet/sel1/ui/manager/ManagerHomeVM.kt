package com.ugnet.sel1.ui.manager


////add issues later
//@HiltViewModel
//class ManagerHomeVM @Inject constructor(private val ownedHuizenUseCase: GetOwnedHuizenUseCase ,private val adresUseCase:GetAdresUseCase) : ViewModel() {
//    var buildings = MutableLiveData<List<BuildingData>>()
//
//
//}

data class BuildingData(val name: String, val address: String, val id: String)

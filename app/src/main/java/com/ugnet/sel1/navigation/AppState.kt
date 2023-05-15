package com.ugnet.sel1.navigation


/*
Copyright 2022 Google LLC

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
import android.content.res.Resources
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.ugnet.sel1.common.snackbar.SnackbarManager
import com.ugnet.sel1.common.snackbar.SnackbarMessage.Companion.toMessage
import com.ugnet.sel1.domain.models.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch



@Stable
class AppState(
    val scaffoldState: ScaffoldState,
    val navController: NavHostController,
    private val snackbarManager: SnackbarManager,
    private val resources: Resources,
    coroutineScope: CoroutineScope
) {
    init {
        coroutineScope.launch {
            snackbarManager.snackbarMessages.filterNotNull().collect { snackbarMessage ->
                val text = snackbarMessage.toMessage(resources)
                scaffoldState.snackbarHostState.showSnackbar(text)
            }
        }
    }


    var shouldRefreshData by mutableStateOf(false)

    val userState = FirebaseAuth.getInstance().currentUser != null

    private val _role  = mutableStateOf("")
    val role: State<String?> get() = _role


    private val _propId = mutableStateOf("")
    val propid: State<String?> get() = _propId

    fun setPropid(propid:String){
        _propId.value = propid
    }

    fun setRole(role: String) {
        _role.value = role
    }

    private val _user = mutableStateOf<User?>(null)
    val user: State<User?> get() = _user


    fun navigate(route: String) {
        navController.navigate(route) { launchSingleTop = true }
    }

    fun navigateBack(){
        navController.popBackStack()
    }

    fun navigateAndPopUp(route: String, popUp: String) {
        navController.navigate(route) {
            popUpTo(popUp) { inclusive = true
                saveState = false}
        }
    }

    fun clearAndNavigate(route: String) {
        navController.navigate(route) {
            popUpTo(0) {
                inclusive = true
            }
            launchSingleTop = true
        }
    }

}

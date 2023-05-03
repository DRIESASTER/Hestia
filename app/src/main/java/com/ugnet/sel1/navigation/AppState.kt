package com.ugnet.sel1.navigation

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
            popUpTo(popUp) { inclusive = true }
        }
    }

    fun clearAndNavigate(route: String) {
        navController.navigate(route) {
            popUpTo(navController.graph.startDestinationId) {
                inclusive = true
            }
            launchSingleTop = true
        }
    }
}

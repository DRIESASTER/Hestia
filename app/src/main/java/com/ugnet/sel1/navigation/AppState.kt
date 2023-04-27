package com.ugnet.sel1.navigation

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.ugnet.sel1.AuthViewModel
import com.ugnet.sel1.domain.models.User
import com.ugnet.sel1.snackbar.SnackbarManager
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberAppState(): AppState {
    val navController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()
    val snackbarManager = SnackbarManager(coroutineScope)
    return remember { AppState(navController, coroutineScope, snackbarManager) }
}

class AppState(
    val navController: NavHostController,
    private val coroutineScope: CoroutineScope,
    private val snackbarManager: SnackbarManager
) {

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
        navController.navigate(route)
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

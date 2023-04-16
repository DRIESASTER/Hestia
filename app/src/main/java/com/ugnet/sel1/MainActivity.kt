package com.ugnet.sel1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.compose.rememberNavController
import com.ugnet.sel1.navigation.MyDestinations
import com.ugnet.sel1.navigation.NavGraph


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    private val viewModel by viewModels<AuthViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            navController = rememberNavController()
            NavGraph(navController = navController)
            AuthState()
        }
    }


    // bron : https://medium.com/firebase-developers/how-to-authenticate-to-firebase-using-email-and-password-in-jetpack-compose-bd70ca56ea91
    @Composable
    private fun AuthState() {
        val isUserSignedOut = viewModel.getAuthState().collectAsState().value
        if (isUserSignedOut) { // Navigate to Role Selection Screen if user is signed out
            NavigateToRoleSelectionScreen()
        } else {
            NavigateToProfileScreen()
        }
    }

    private @Composable
    fun NavigateToRoleSelectionScreen() =  navController.navigate(MyDestinations.ROLE_SELECTION_ROUTE){
        popUpTo(navController.graph.id) {
            inclusive = true
        }
    }

    @Composable
    fun NavigateToProfileScreen() = navController.navigate(MyDestinations.PROFILE_ROUTE) {
        popUpTo(navController.graph.id) {
            inclusive = true
        }
    }

}




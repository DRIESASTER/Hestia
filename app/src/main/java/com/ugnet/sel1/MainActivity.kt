package com.ugnet.sel1


import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.compose.rememberNavController
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.domain.repository.oudeShit.Adreses
import com.ugnet.sel1.navigation.MyDestinations
import com.ugnet.sel1.navigation.NavGraph
import com.ugnet.sel1.presentation.profile.adres.AdresesScreen
import com.ugnet.sel1.ui.manager.ManagerHomeScreen


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
        val userResponse = viewModel.userResponse.collectAsState().value

        if (isUserSignedOut) { // Navigate to Role Selection Screen if user is signed out
            NavigateToRoleSelectionScreen()
        } else {
            NavigateToProfileScreen()
        }
           /* when (userResponse) {
                is Response.Loading -> {
                    // Show a progress indicator while loading the user's role
                    CircularProgressIndicator()
                }
                is Response.Success -> {
                    val userRole = userResponse.data?.accountType
                    when (userRole) {
                        "Manager" -> {
                            NavigateToManagerHomeScreen()
                        }
                        "Hiree" -> {
                            NavigateToHireeHomeScreen()
                        }
                        else -> {
                            if (userRole != null) {
                                Log.d("ROLE", userRole)
                            }
                            // Handle other roles or error cases
                        }
                    }
                }
                is Response.Failure -> {
                    // Handle the error
                }
            }
        }*/
    }



    @Composable
    fun NavigateToManagerHomeScreen() {
        navController.navigate(MyDestinations.MANAGER_HOME_ROUTE) {
            popUpTo(navController.graph.id) {
                inclusive = true
            }
        }
    }

    @Composable
    fun NavigateToHireeHomeScreen() {
        navController.navigate(MyDestinations.HIREE_HOME_ROUTE) {
            popUpTo(navController.graph.id) {
                inclusive = true
            }
        }
    }

    @Composable
    fun NavigateToRoleSelectionScreen() =
        navController.navigate(MyDestinations.ROLE_SELECTION_ROUTE) {
            popUpTo(navController.graph.id) {
                inclusive = true
            }
        }

    @Composable
    fun NavigateToProfileScreen() {
        navController.navigate(MyDestinations.PROFILE_ROUTE) {
            popUpTo(navController.graph.id) {
                inclusive = true
            }
        }
    }

}




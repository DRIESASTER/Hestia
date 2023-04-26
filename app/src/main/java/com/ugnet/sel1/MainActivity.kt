package com.ugnet.sel1


import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.compose.rememberNavController
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.domain.repository.oudeShit.Adreses
import com.ugnet.sel1.navigation.HestiaApp
import com.ugnet.sel1.navigation.MyDestinations
import com.ugnet.sel1.navigation.NavGraph
import com.ugnet.sel1.presentation.profile.adres.AdresesScreen
import com.ugnet.sel1.ui.manager.ManagerHomeScreen


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<AuthViewModel>()

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HestiaApp(viewModel)

/*            navController = rememberNavController()
            NavGraph(navController = navController)
            AuthState()*/
        }
    }


    // bron : https://medium.com/firebase-developers/how-to-authenticate-to-firebase-using-email-and-password-in-jetpack-compose-bd70ca56ea91


/*    @Composable
    private fun AuthState() {
        val isUserSignedOut = viewModel.getAuthState().collectAsState().value
        if (isUserSignedOut) {
            NavigateToSignInScreen()
        } else {
            if (viewModel.isEmailVerified) {
                NavigateToProfileScreen()
            } else {
                NavigateToVerifyEmailScreen()
            }
        }
    }*/

    // todo work with app state to updata en get acces to userdata
    // todo route to right home screen
    //@Composable
    /*private fun AuthState() {
        //val isUserSignedOut = viewModel.getAuthState().collectAsState().value
        val userResponse = viewModel.userResponse.collectAsState().value
        when (userResponse) {
            is Response.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is Response.Success -> {
                val user = (userResponse as Response.Success).data
                if (user?.accountType == "Manager") {
                    navController.navigate(MyDestinations.MANAGER_HOME_ROUTE) {
                        popUpTo(navController.graph.id) { inclusive = true }
                    }
                } else if (user?.accountType == "Huurder") {
                    navController.navigate(MyDestinations.HIREE_HOME_ROUTE) {
                        popUpTo(navController.graph.id) { inclusive = true }
                    }
                } else {
                    navController.navigate(MyDestinations.ROLE_SELECTION_ROUTE) {
                        popUpTo(navController.graph.id) { inclusive = true }
                    }
                }
            }
            is Response.Failure -> {
                navController.navigate(MyDestinations.ROLE_SELECTION_ROUTE) {
                    popUpTo(navController.graph.id) { inclusive = true }
                }
            }
        }
    }
*/


    /*@Composable
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
    }*/

}




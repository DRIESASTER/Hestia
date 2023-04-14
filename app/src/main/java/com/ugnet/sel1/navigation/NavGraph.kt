package com.ugnet.sel1.navigation


import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
//import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.launch
import kotlin.math.sign
import android.content.Context
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext


@Composable
fun MyNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = MyDestinations.LOGIN_ROUTE
) {

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()



    NavHost(navController = navController, startDestination = startDestination) {
        composable(MyDestinations.LOGIN_ROUTE) {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "sign_in") {
                composable("sign_in") {

                }
            }

/*        composable(MyDestinations.MAIN_ROUTE) {
            MainRoute(
                vM = hiltViewModel(),
                onClickToDetails = {
                    navController.navigate(MyDestinations.ROUTE_A)
                }
            )
        }*/
            // Add other routes here
        }
    }
}
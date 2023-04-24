package com.ugnet.sel1.navigation


import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import com.ugnet.sel1.AuthViewModel
import com.ugnet.sel1.navigation.NavGraphBuilder.hestiaGraph


@Composable
@ExperimentalMaterialApi
fun HestiaApp(viewModel: AuthViewModel) {


    Surface(color = MaterialTheme.colors.background) {
        val appState = rememberAppState()
            NavHost(
                navController = appState.navController,
                startDestination = MyDestinations.SPLASH_ROUTE,
            ) {
                hestiaGraph(appState, viewModel)

            }
    }
}


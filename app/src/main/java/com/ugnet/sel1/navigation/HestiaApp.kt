package com.ugnet.sel1.navigation


import android.content.res.Resources
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.ugnet.sel1.AuthViewModel
import com.ugnet.sel1.common.snackbar.SnackbarManager
import com.ugnet.sel1.navigation.NavGraphBuilder.hestiaGraph
import kotlinx.coroutines.CoroutineScope



@Composable
@ExperimentalMaterialApi
fun HestiaApp(viewModel: AuthViewModel) {
    Surface(color = MaterialTheme.colors.background) {
        val appState = rememberAppState()
        val startDestination = when (appState.userState) {
            true -> MyDestinations.SPLASH_ROUTE
            false -> MyDestinations.ROLE_SELECTION_ROUTE
        }
        Scaffold(
            snackbarHost = {
                SnackbarHost(
                    hostState = it,
                    modifier = Modifier.padding(8.dp),
                    snackbar = { snackbarData ->
                        Snackbar(snackbarData, contentColor = MaterialTheme.colors.onPrimary)
                    }
                )
            },
            scaffoldState = appState.scaffoldState
        ) { innerPaddingModifier ->
            NavHost(
                navController = appState.navController,
                startDestination = startDestination,
                modifier = Modifier.padding(innerPaddingModifier)
            ) {
                hestiaGraph(appState, viewModel)
            }
        }
    }

}



@Composable
fun rememberAppState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController = rememberNavController(),
    snackbarManager: SnackbarManager = SnackbarManager,
    resources: Resources = resources(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) =
    remember(scaffoldState, navController, snackbarManager, resources, coroutineScope) {
        AppState(scaffoldState, navController, snackbarManager, resources, coroutineScope)
    }

@Composable
@ReadOnlyComposable
fun resources(): Resources {
    LocalConfiguration.current
    return LocalContext.current.resources
}


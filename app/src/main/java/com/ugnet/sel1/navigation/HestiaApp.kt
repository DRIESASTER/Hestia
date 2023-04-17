package com.ugnet.sel1.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost


@Composable
@ExperimentalMaterialApi
fun MakeItSoApp() {


}
/*    Surface(color = MaterialTheme.colors.background) {
        val appState = rememberAppState()


        Scaffold(
            snackbarHost = { SnackbarHost(...)
            },
            scaffoldState = appState.scaffoldState
        ) { innerPaddingModifier ->
            NavHost(
                navController = appState.navController,
                startDestination = SPLASH_SCREEN,
                modifier = Modifier.padding(innerPaddingModifier)
            ) {
                NavGraph(navController = )
            }
        }
    }*/


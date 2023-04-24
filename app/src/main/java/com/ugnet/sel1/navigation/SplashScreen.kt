package com.ugnet.sel1.navigation

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ugnet.sel1.AuthViewModel
import com.ugnet.sel1.domain.models.Response



@Composable
fun SplashScreen(
    navigate: (String) -> Unit,
    viewModel: AuthViewModel,
    ) {
    val isSignOut = viewModel.getAuthState().collectAsState().value

    if (isSignOut) {
        Log.d("LOGGED OUT", isSignOut.toString())
        navigate(MyDestinations.ROLE_SELECTION_ROUTE)
    } else {
        Log.d("SPALSSCRRENUSERRESPOSNE", viewModel.userResponse.toString())
        LaunchedEffect(isSignOut, viewModel.userResponse) {
            when (viewModel.userResponse) {
                is Response.Loading -> {
                    // No action required
                }
                is Response.Success -> {
                    val user = (viewModel.userResponse as Response.Success).data
                    if (user?.accountType == "Manager") {
                        navigate(MyDestinations.MANAGER_HOME_ROUTE)
                    } else if (user?.accountType == "Hiree") {
                        navigate(MyDestinations.HIREE_HOME_ROUTE)
                    } else {
                        navigate(MyDestinations.ROLE_SELECTION_ROUTE)
                    }
                }
                is Response.Failure -> {
                    navigate(MyDestinations.ROLE_SELECTION_ROUTE)
                }
            }
        }
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}












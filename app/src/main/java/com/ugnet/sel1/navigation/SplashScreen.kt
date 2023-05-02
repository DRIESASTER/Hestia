package com.ugnet.sel1.navigation

import android.util.Log
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.ugnet.sel1.AuthViewModel
import com.ugnet.sel1.common.snackbar.SnackbarManager
import com.ugnet.sel1.domain.models.Response


import com.ugnet.sel1.R.string as AppText

@Composable
fun SplashScreen(
    navigate: (String) -> Unit,
    viewModel: AuthViewModel,
    ) {
    Log.d("userReponse", viewModel.userResponse.toString())
    LaunchedEffect(viewModel.userResponse) {
        when (viewModel.userResponse) {
            is Response.Loading -> {
                // No action required
            }
            is Response.Success -> {
                val user = (viewModel.userResponse as Response.Success).data
                if (user?.accountType == "Manager") {
                    navigate(MyDestinations.MANAGER_HOME_ROUTE)
                } else if (user?.accountType == "Huurder") {
                    navigate(MyDestinations.HIREE_HOME_ROUTE)
                } else {
                    SnackbarManager.showMessage(AppText.generic_error)
                    navigate(MyDestinations.ROLE_SELECTION_ROUTE)
                }
            }
            is Response.Failure -> {
                SnackbarManager.showMessage(AppText.generic_error);
                navigate(MyDestinations.ROLE_SELECTION_ROUTE)
            }
        }
    }
    if(viewModel.userResponse is Response.Loading){
        CircularProgressIndicator()
    }
}












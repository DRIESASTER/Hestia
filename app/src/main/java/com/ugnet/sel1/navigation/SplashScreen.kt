package com.ugnet.sel1.navigation

import android.util.Log
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.ugnet.sel1.AuthViewModel
import com.ugnet.sel1.common.snackbar.SnackbarManager
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.ui.theme.MainGroen


import com.ugnet.sel1.R.string as AppText

@Composable
fun SplashScreen(
    //openAndPopUp : (String, String) -> Unit,
    clearAndNavigate : (String) -> Unit,
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
                    //openAndPopUp(MyDestinations.MANAGER_HOME_ROUTE, MyDestinations.ROLE_SELECTION_ROUTE)
                    clearAndNavigate(MyDestinations.MANAGER_HOME_ROUTE)
                    viewModel.userResponse = Response.Loading
                } else if (user?.accountType == "Huurder") {
                    clearAndNavigate(MyDestinations.HIREE_HOME_ROUTE)
                    viewModel.userResponse = Response.Loading
                    //openAndPopUp(MyDestinations.HIREE_HOME_ROUTE, MyDestinations.ROLE_SELECTION_ROUTE)
                } else {
                    SnackbarManager.showMessage(AppText.generic_error)
                    //openAndPopUp(MyDestinations.ROLE_SELECTION_ROUTE, MyDestinations.ROLE_SELECTION_ROUTE)
                }
            }
            is Response.Failure -> {
                SnackbarManager.showMessage(AppText.generic_error);
                //openAndPopUp(MyDestinations.ROLE_SELECTION_ROUTE, MyDestinations.SPLASH_ROUTE)
            }
        }
    }
    if(viewModel.userResponse is Response.Loading){
        CircularProgressIndicator(color = MainGroen)
    }
}












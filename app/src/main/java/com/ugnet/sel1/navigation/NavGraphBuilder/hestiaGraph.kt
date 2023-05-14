package com.ugnet.sel1.navigation.NavGraphBuilder

import android.util.Log
import androidx.compose.material.ExperimentalMaterialApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ugnet.sel1.AuthViewModel
import com.ugnet.sel1.authentication.forgot_password.ForgotPasswordScreen
import com.ugnet.sel1.navigation.SplashScreen
import com.ugnet.sel1.authentication.login.LoginScreen
import com.ugnet.sel1.authentication.profile.UserProfileScreen
import com.ugnet.sel1.authentication.selection.RoleSelectionScreen
import com.ugnet.sel1.authentication.signup.SignUpScreen
import com.ugnet.sel1.navigation.AppState
import com.ugnet.sel1.navigation.MyDestinations
import com.ugnet.sel1.ui.announcements.AnnouncementScreen
import com.ugnet.sel1.ui.announcements.AnnouncementsAddScreen
import com.ugnet.sel1.ui.manager.ManagerHomeScreen
import com.ugnet.sel1.ui.manager.addProp.AddPropMainScreen
import com.ugnet.sel1.ui.manager.addProp.RoomeditScreenApp
import com.ugnet.sel1.ui.manager.addProp.RoomeditScreenHouse
import com.ugnet.sel1.ui.manager.issues.IssueRouteScreen
import com.ugnet.sel1.ui.manager.properties.PropertyDetailRoute
import com.ugnet.sel1.ui.resident.AddIssueScreen
import com.ugnet.sel1.ui.resident.ResidentHomeScreen

@ExperimentalMaterialApi
fun NavGraphBuilder.hestiaGraph(appState: AppState, viewModel: AuthViewModel) {


    composable(MyDestinations.ROLE_SELECTION_ROUTE) {
        Log.d("ROLESELECTOIN", "ROUTE")
        RoleSelectionScreen(
            navigate = { route -> appState.navigate(route) }
        ) { role: String -> appState.setRole(role) }

    }

    composable(MyDestinations.SPLASH_ROUTE) {
        SplashScreen(navigate = { route -> appState.navigate(route) }, viewModel)
    }

    composable(MyDestinations.LOGIN_ROUTE) {
        LoginScreen(navigate = { route -> appState.navigate(route) })
    }
    
    composable(MyDestinations.FORGOT_PASSWORD_ROUTE){
        ForgotPasswordScreen(navigateTo = { route -> appState.navigate(route) })
    }

    composable(MyDestinations.SIGN_UP_ROUTE) {
        SignUpScreen(
            openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) },
            role = appState.role
        )
    }

    composable(MyDestinations.PROFILE_ROUTE) {
        UserProfileScreen(navController = appState.navController,
        clearAndNavigate = { route -> appState.clearAndNavigate(route) })
    }

    composable(MyDestinations.MANAGER_HOME_ROUTE) {
        ManagerHomeScreen(Data = hiltViewModel(), navigate = { route -> appState.navigate(route) })
    }


    composable(MyDestinations.ROOM_EDIT_ROUTE_APP + "/{email}/{propId}") {
        RoomeditScreenApp { route -> appState.navigate(route) }
    }

    composable(
        MyDestinations.ISSUE_ROUTE,
    ) { backStackEntry ->
        //val issueId = backStackEntry.arguments?.getString(MyDestinations.IssueArgs.IssueId)!!
        Log.d("ROUTING Issue edit", "")
        IssueRouteScreen(viewModel = hiltViewModel(), navigateBack = { appState.navigateBack() })
    }

    composable(MyDestinations.ROOM_EDIT_ROUTE_HOUSE +  "/{email}/{propId}") {
        Log.d("ROUTING Issue edit", "")
        RoomeditScreenHouse(viewModel = hiltViewModel(), navigate = { route -> appState.navigate(route) })
    }

    composable(MyDestinations.EDIT_PROPERTY_ROUTE + "/{propId}"){
        AddPropMainScreen(navigate = { route -> appState.navigate(route) })
    }

    composable(MyDestinations.PROPERTY_DETAILS_ROUTE +  "/{propId}"){
        PropertyDetailRoute(viewModel = hiltViewModel(), navigate = { route -> appState.navigate(route) }, navigateBack = { appState.navigateBack() })
    }

    composable(MyDestinations.ANNOUNCEMENT_ROUTE){
        AnnouncementScreen(viewModel = hiltViewModel(), navigate = { route -> appState.navigate(route) })
    }

    composable(MyDestinations.ANNOUNCEMENT_ADD_ROUTE){
        AnnouncementsAddScreen(viewModel = hiltViewModel()) {

        }
    }


    composable(MyDestinations.ADD_ISSUE_ROUTE){
        AddIssueScreen(navigate = { route -> appState.navigate(route) }, viewModel = hiltViewModel())
    }

    //composable(MyDestinations.)


    composable(MyDestinations.HIREE_HOME_ROUTE) {
        ResidentHomeScreen(navigate =  { route -> appState.navigate(route) })
    }

    composable(MyDestinations.ADD_PROPERTY + "/{propId}") {
        AddPropMainScreen(navigate =  { route -> appState.navigate(route) }
        )
    }



    // roomeditscreen


/*    composable(SETTINGS_SCREEN) {
        SettingsScreen(
            restartApp = { route -> appState.clearAndNavigate(route) },
            openScreen = { route -> appState.navigate(route) }
        )
    }*/
}


package com.ugnet.sel1.navigation.NavGraphBuilder

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ugnet.sel1.AuthViewModel
import com.ugnet.sel1.navigation.SplashScreen
import com.ugnet.sel1.authentication.login.LoginScreen
import com.ugnet.sel1.authentication.profile.UserProfileScreen
import com.ugnet.sel1.authentication.selection.RoleSelectionScreen
import com.ugnet.sel1.authentication.signup.SignUpScreen
import com.ugnet.sel1.navigation.AppState
import com.ugnet.sel1.navigation.MyDestinations
import com.ugnet.sel1.presentation.profile.ProfileScreen
import com.ugnet.sel1.ui.manager.ManagerHomeScreen
import com.ugnet.sel1.ui.manager.addProp.AddPropMainScreen
import com.ugnet.sel1.ui.resident.ResidentHomeScreen

@ExperimentalMaterialApi
fun NavGraphBuilder.hestiaGraph(appState: AppState, viewModel: AuthViewModel) {


    composable(MyDestinations.ROLE_SELECTION_ROUTE) {
        RoleSelectionScreen(
            openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) }
        ) { role: String -> appState.setRole(role) }

    }

    composable(MyDestinations.SPLASH_ROUTE) {
        SplashScreen(navigate = { route -> appState.navigate(route) }, viewModel)
    }

    composable(MyDestinations.LOGIN_ROUTE) {
        LoginScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }

    composable(MyDestinations.SIGN_UP_ROUTE) {
        SignUpScreen(
            openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) },
            role = appState.role
        )
    }

/*    composable(
        route = MyDestinations.SIGN_UP_ROUTE,
        arguments = listOf(navArgument("role") { type = NavType.StringType })
    ) { backStackEntry ->
        val role = backStackEntry.arguments?.getString("role")
        if (role != null) {
            SignUpScreen(
                role = role,
                openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) }
            )
        }
    }*/
    composable(MyDestinations.PROFILE_ROUTE) {
        UserProfileScreen(navController = appState.navController)
    }

    composable(MyDestinations.MANAGER_HOME_ROUTE) {
        ManagerHomeScreen(openAndPopUp = { route, popUp ->
            appState.navigateAndPopUp(
                route,
                popUp
            )
        })
    }

    composable(MyDestinations.HIREE_HOME_ROUTE) {
        ResidentHomeScreen()
    }

    composable(MyDestinations.ADD_PROPERTY) {
        AddPropMainScreen(modifier = Modifier)
    }


/*    composable(SETTINGS_SCREEN) {
        SettingsScreen(
            restartApp = { route -> appState.clearAndNavigate(route) },
            openScreen = { route -> appState.navigate(route) }
        )
    }*/
}


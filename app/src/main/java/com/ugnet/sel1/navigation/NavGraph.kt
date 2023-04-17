package com.ugnet.sel1.navigation




import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ugnet.sel1.authentication.login.LoginScreen
import com.ugnet.sel1.authentication.selection.RoleSelectionScreen
import com.ugnet.sel1.authentication.signup.SignUpScreen
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.ugnet.sel1.authentication.login.SignInViewModel
import com.ugnet.sel1.authentication.profile.UserProfileScreen
import com.ugnet.sel1.authentication.selection.RoleSelectionViewModel
import com.ugnet.sel1.authentication.signup.SignUpViewModel
import com.ugnet.sel1.presentation.profile.ProfileScreen


@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String = MyDestinations.ROLE_SELECTION_ROUTE
) {
    val signInViewModel: SignInViewModel = hiltViewModel()
    val signUpViewModel : SignUpViewModel = hiltViewModel()
    val roleSelectionViewModel : RoleSelectionViewModel = hiltViewModel()


    NavHost(navController = navController, startDestination = startDestination) {
        composable(MyDestinations.ROLE_SELECTION_ROUTE) {
            RoleSelectionScreen(navController, roleSelectionViewModel)
        }
        composable(MyDestinations.LOGIN_ROUTE) {
            LoginScreen(signInViewModel, navController,
                login = { email, password ->
                    signInViewModel.signInWithEmailAndPassword(email, password)
                })
        }
        composable(MyDestinations.SIGN_UP_ROUTE) {
            SignUpScreen(
                roleSelectionViewModel = roleSelectionViewModel,
                viewModel = signUpViewModel,
                navController = navController,
                signUp = { email, password, role, name, surname ->
                signUpViewModel.signUpWithEmailAndPassword(email, password, role, name, surname)
            })
        }
        composable(MyDestinations.PROFILE_ROUTE) {

            print("destination profile_ROUTE")

            UserProfileScreen()
        }
        // Add any other destinations here
    }
}




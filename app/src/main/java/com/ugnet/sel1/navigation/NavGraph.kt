package com.ugnet.sel1.navigation




import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ugnet.sel1.authentication.selection.RoleSelectionScreen
import com.ugnet.sel1.authentication.signup.SignUpScreen
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.ugnet.sel1.authentication.login.SignInViewModel
import com.ugnet.sel1.authentication.profile.UserProfileScreen
import com.ugnet.sel1.authentication.selection.RoleSelectionViewModel
import com.ugnet.sel1.authentication.signup.SignUpViewModel
import com.ugnet.sel1.ui.manager.ManagerHomeScreen
import com.ugnet.sel1.ui.manager.addProp.AddPropMainScreen
import com.ugnet.sel1.ui.resident.ResidentHomeScreen



// todo work with screens and appstate, and pass nav functions instead of navcontroller
@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String = MyDestinations.ROLE_SELECTION_ROUTE
) {




    val signInViewModel: SignInViewModel = hiltViewModel()
    val signUpViewModel : SignUpViewModel = hiltViewModel()
    val roleSelectionViewModel : RoleSelectionViewModel = hiltViewModel()


    NavHost(navController = navController, startDestination = startDestination) {
   /*     composable(MyDestinations.ROLE_SELECTION_ROUTE) {
            RoleSelectionScreen(navController, roleSelectionViewModel)
        }*/

        composable(MyDestinations.SPLASH_ROUTE) {
            //SplashScreen(navController)
        }

        composable(MyDestinations.LOGIN_ROUTE) {
    /*        LoginScreen(signInViewModel, navController,
                login = { email, password ->
                    signInViewModel.signInWithEmailAndPassword(email, password)
                })*/
        }
        composable(MyDestinations.SIGN_UP_ROUTE) {
  /*          SignUpScreen(
                roleSelectionViewModel = roleSelectionViewModel,
                viewModel = signUpViewModel,
                navController = navController,
                signUp = { email, password, role, name, surname ->
                signUpViewModel.signUpWithEmailAndPassword(email, password, role, name, surname)
            })*/
        }
        composable(MyDestinations.PROFILE_ROUTE) {
            UserProfileScreen(navController = navController)
        }

 /*       composable(MyDestinations.MANAGER_HOME_ROUTE) {
            ManagerHomeScreen(openAndPopUp = )
        }*/

        composable(MyDestinations.HIREE_HOME_ROUTE) {
            ResidentHomeScreen()
        }

        composable(MyDestinations.ADD_PROPERTY){
            AddPropMainScreen(modifier = androidx.compose.ui.Modifier)
        }



        // add prop main screen

        // Add any other destinations here
    }
}




package com.ugnet.sel1.authentication.profile

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Campaign
import androidx.compose.material.icons.rounded.ExitToApp
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.domain.models.User
import com.ugnet.sel1.navigation.MyDestinations
import com.ugnet.sel1.ui.components.*
import com.ugnet.sel1.ui.theme.MainGroen
import kotlinx.coroutines.launch


@Composable
fun UserProfileScreen(
    userViewModel: UserViewModel = hiltViewModel(),
    navController: NavController,
    clearAndNavigate: (String) -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    fun navigateToHomeScreen(user : User):String {
        var route = ""
         when (user.accountType) {
            "Manager" -> route = MyDestinations.MANAGER_HOME_ROUTE
            "Huurder" -> route = MyDestinations.HIREE_HOME_ROUTE
        }
        return route
    }
//    Log.d("USER", Firebase.auth.currentUser?.uid.toString())


        userViewModel.getUser(Firebase.auth.currentUser?.uid.toString())
        when (val response = userViewModel.userDataResponse) {
            is Response.Loading -> {
                CircularProgressIndicator(color = MainGroen)
            }
            is Response.Success -> {
                Log.d("resposne succes", response.data.toString())
                val user = response.data

                val drawerItems = listOf(
                    MenuItem(
                        name = "Home",
                        route = navigateToHomeScreen(user = user!!),
                        icon = Icons.Rounded.Home,
                    ),
                    MenuItem(
                        name = "Logout",
                        route = MyDestinations.ROLE_SELECTION_ROUTE,
                        icon = Icons.Rounded.ExitToApp
                    ),

                    MenuItem(
                        name = "Profile",
                        route = MyDestinations.PROFILE_ROUTE,
                        icon = Icons.Rounded.Person,
                    ),
                    MenuItem(
                        name = "Announcements",
                        route = MyDestinations.ANNOUNCEMENT_ROUTE,
                        icon = Icons.Rounded.Campaign,
                    )
                )



                response.data?.let { user ->

                    Scaffold(topBar = {
                        ResidentTopBar(
                            onNavigationIconClick = {
                                Log.d("Drawer", "Clicked")
                                coroutineScope.launch {
                                    scaffoldState.drawerState.open()
                                }
                            }, topBarTitle = "Profile"
                        )
                    }, scaffoldState = scaffoldState, drawerContent = {
                        DrawerHeader()
                        DrawerBody(items=drawerItems,onItemClick={ item ->
                            if(item.name == "Logout"){
                                userViewModel.signOut()
                                clearAndNavigate(MyDestinations.ROLE_SELECTION_ROUTE)
                            } else{
                                navController.navigate(item.route)
                            }
                        })
                    }) { paddingValues ->
                        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center,modifier=Modifier.fillMaxSize().padding(10.dp)) {
                            ProfileCard(
                                name = user.voornaam + " " + user.achternaam,
                                email = user.email!!,
                            )
                            Spacer(modifier = Modifier.height(16.dp))

                            Button(
                                onClick = {
                                    userViewModel.signOut()
                                    clearAndNavigate(MyDestinations.ROLE_SELECTION_ROUTE)
                                },
                                modifier = Modifier
                                    .wrapContentWidth(Alignment.CenterHorizontally),
                                colors = ButtonDefaults.buttonColors(backgroundColor = MainGroen)
                            ) {
                                Text(
                                    text = "Sign Out",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                }}}
                is Response.Failure -> {
                    Text(text = "Error: ${response.e?.localizedMessage}", color = Color.Red)
                }
        }
}





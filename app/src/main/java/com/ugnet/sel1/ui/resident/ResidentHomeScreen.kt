package com.ugnet.sel1.ui.resident

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.hilt.navigation.compose.hiltViewModel
import com.ugnet.sel1.ui.components.DrawerBody
import com.ugnet.sel1.ui.components.DrawerHeader
import com.ugnet.sel1.ui.components.MenuItem
import com.ugnet.sel1.ui.components.ResidentTopBar
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ResidentHomeScreen(
    viewModel: ResidentHomeVM = hiltViewModel(),
    navigate : (String) -> Unit,
    cScreen: String = "Profile"
) {
    val drawerItems = listOf(
        MenuItem(
            name = "Profile",
            route = "profile",
            icon = Icons.Rounded.Person,
        ),
        MenuItem(
            name = "Issues",
            route = "issues",
            icon = Icons.Rounded.Notifications,
        ),
        MenuItem(
            name = "Logout",
            route = "logout",
            icon = Icons.Rounded.Logout,
        ),
    )

    var currentTitle by rememberSaveable { mutableStateOf(cScreen) }
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            ResidentTopBar(
                onNavigationIconClick = {
                    coroutineScope.launch {
                        scaffoldState.drawerState.open()
                    }
                },
                topBarTitle = currentTitle
            )
        },
        drawerContent = {
            DrawerHeader()
            DrawerBody(
                items = drawerItems,
                onItemClick = {
                    if(it.name == "Logout") {
                        //TODO: call log out function
                    }
                    currentTitle = it.name
                    coroutineScope.launch {
                        scaffoldState.drawerState.close()
                    }
                }
            )
        },
        content = {
            GetCorrectDisplay(currentTitle, viewModel, navigate)
        }
    )
}

@Composable
fun GetCorrectDisplay(title:String = "Profile", vm:ResidentHomeVM, navigate: (String) -> Unit) {
    when (title) {
        "Profile" -> ResidentProfileScreen(viewModel = vm)
        "Issues" -> ResidentIssueOverview(viewModel = vm, navigate = navigate)
        "Logout" -> Text(text = "TODO: implement Logout")
    }
}
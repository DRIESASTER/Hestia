package com.ugnet.sel1.ui.resident

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.ugnet.sel1.ui.components.DrawerBody
import com.ugnet.sel1.ui.components.DrawerHeader
import com.ugnet.sel1.ui.components.MenuItem
import com.ugnet.sel1.ui.components.ResidentTopBar
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ResidentHomeScreen(Data: ResidentHomeVM = hiltViewModel()) {
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

    var currentTitle by rememberSaveable { mutableStateOf(drawerItems[0].name) }
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
                    currentTitle = it.name
                }
            )
        },
        content = { GetCorrectDisplay(currentTitle, Data) }
    )
}

//TODO: pass actual data and implement all screens
@Composable
fun GetCorrectDisplay(title:String = "Profile", vm:ResidentHomeVM) {
    when (title) {
        "Profile" -> Text(text = "TODO: implement profile screen")
        "Issues" -> ResidentIssueOverview(data = vm,issues = emptyList())
        "Logout" -> Text(text = "TODO: implement Logout")
    }
}

@Preview
@Composable
fun ResidentHomeScreenPreview() {
    ResidentHomeScreen()
}
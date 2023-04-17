package com.ugnet.sel1.ui.manager

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.ui.components.*
import com.ugnet.sel1.ui.theme.MainGroen
import kotlinx.coroutines.launch

@Composable
fun ManagerHomeScreen(Data:ManagerHomeVM=hiltViewModel()){

    //data
    val drawerItems = listOf(
        MenuItem(
            name = "Home",
            route = "managerhome",
            icon = Icons.Rounded.Home,
        ),
        MenuItem(name= "Logout",
            route = "logout",
            icon = Icons.Rounded.ExitToApp),
        MenuItem(
            name = "Chat",
            route = "chat",
            icon = Icons.Rounded.Chat,
        ),
        MenuItem(
            name = "Profile",
            route = "profile",
            icon = Icons.Rounded.Person,
        ),
        MenuItem(
            name = "Announcements",
            route = "announcements",
            icon = Icons.Rounded.Campaign
        )
    )

    var currentTitle by rememberSaveable { mutableStateOf(drawerItems[0].name) }
    val scaffoldState = rememberScaffoldState()
    val coroutineScope  = rememberCoroutineScope()

    //ui
    Scaffold(modifier = Modifier.fillMaxSize(1.0f),scaffoldState = scaffoldState,
        topBar = {ResidentTopBar(
        onNavigationIconClick = {coroutineScope.launch {
            scaffoldState.drawerState.open()
        } },
        topBarTitle = currentTitle
    )}, drawerContent = {
            DrawerHeader()
            DrawerBody(items=drawerItems,onItemClick={})
        }, content={ padding ->

        Box(contentAlignment = Alignment.TopStart,modifier = Modifier.padding(padding).fillMaxWidth()){
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                SwitchButton2(
                    option1 = "Issues",
                    option2 = "Properties",
                    initialState = Data.currentState,
                    onStateChanged = {
                        Data.currentState = it
                    }
                )
            }

            Column(horizontalAlignment = Alignment.Start) {
                if (!Data.currentState) {
                    /*show issues overview*/
                    IssueOverview(modifier = Modifier.padding(padding),
                        issues = when (val allissues = Data.issuesForManagerResponse) {
                            is Response.Success -> allissues.data
                            else -> listOf()
                        },
                        onIssueClicked = {/*route to DetailsScreen*/ },
                        onStatusClicked = { status, issue, building ->
                            Data.changeIssueStatus(issue, status, building)
                        })
                } else {
                    /*show properties overview*/
                    when (val allproperties = Data.ownedPropertiesResponseFormatted) {
                        is Response.Success -> {
                            if (allproperties.data.isEmpty()) {
                                Text(text = "No properties found")
                            } else {
                                PropertyOverview(
                                    properties = allproperties.data,
                                    onPropertyClicked = {/*route to details*/ })
                            }
                        }
                        else -> {
                            Text(text = "Loading properties...")
                        }
                    }
                }
            }
            }
    },
        floatingActionButton = {if (Data.currentState) {addButton(contentDescription = "Add property", onClick = {/**fix routing*/})}}
    )

}


@Composable
fun addButton(contentDescription: String, onClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        FloatingActionButton(
            onClick = onClick,
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            Icon(imageVector = Icons.Rounded.Add, contentDescription = contentDescription, tint = MainGroen)
        }
    }
}


@Preview
@Composable
fun ManagerHomeScreenPreview(){
    ManagerHomeScreen()
}


package com.ugnet.sel1.ui.manager

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.navigation.MyDestinations
import com.ugnet.sel1.ui.components.*
import com.ugnet.sel1.ui.theme.MainGroen
import kotlinx.coroutines.launch

@Composable
fun ManagerHomeScreen(Data:ManagerHomeVM=hiltViewModel(), initialScreen:Boolean=false, navController: NavController){

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
            name = "Events",
            route = "events",
            icon = Icons.Rounded.CalendarToday
        ),
        MenuItem(
            name = "Announcements",
            route = "announcements",
            icon = Icons.Rounded.Campaign
        )
    )

    var currentTitle by rememberSaveable { mutableStateOf(drawerItems[0].name) }
    val (currentState, setCurrentState) = remember { mutableStateOf(initialScreen) }
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
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(padding)){
            SwitchButton2(
                option1 = "Issues",
                option2 = "Properties",
                initialState = initialScreen,
                onStateChanged = {
                    setCurrentState(it)
                }
            )
            if (!currentState) {
                /*show issues overview*/
                IssueOverview(issues = when (val allissues = Data.issuesForManagerResponse){
                    is Response.Success -> allissues.data
                    else -> listOf()
                }, onIssueClicked = {/*route to DetailsScreen*/}, onStatusClicked = { status,issue,building ->
                    Data.changeIssueStatus(issue, status,building)})
            } else {
                /*show properties overview*/
                PropertyOverview(
                    properties = when (val allproperties = Data.ownedPropertiesResponseFormatted) {
                        is Response.Success -> allproperties.data
                        else -> listOf()
                    }, onPropertyClicked = {/*route to DetailsScreen*/}
                )
            }
            }
    },
        bottomBar = {if (!currentState) {addButton(contentDescription = "Add property", onClick = { navController.navigate(MyDestinations.ADD_PROPERTY) }
        )}}
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
    ManagerHomeScreen(navController = rememberNavController())
}


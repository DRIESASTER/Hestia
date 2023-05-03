package com.ugnet.sel1.ui.manager

import android.util.Log
import androidx.compose.foundation.layout.*
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
import com.ugnet.sel1.navigation.MyDestinations
import com.ugnet.sel1.ui.components.*
import com.ugnet.sel1.ui.theme.AccentLicht
import com.ugnet.sel1.ui.theme.MainGroen
import kotlinx.coroutines.launch

@Composable


fun ManagerHomeScreen(Data:ManagerHomeVM=hiltViewModel(), initialScreen:Boolean=false,
                      navigate: (String) -> Unit){
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
            route = MyDestinations.PROFILE_ROUTE,
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

    Log.d("ManagerHomeScreen","current switchState ${Data.currentState}")
    //ui
    Scaffold(modifier = Modifier.fillMaxSize(1.0f),scaffoldState = scaffoldState,
        topBar = {ResidentTopBar(
        onNavigationIconClick = {coroutineScope.launch {
            scaffoldState.drawerState.open()
        } },
        topBarTitle = currentTitle
    )}, drawerContent = {
            DrawerHeader()
            DrawerBody(items=drawerItems,onItemClick={item -> navigate(item.route)})
        }, content={ padding ->

        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center,modifier = Modifier
            .fillMaxWidth()
            .padding(padding)){
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                SwitchButton2(
                    option1 = "Issues",
                    option2 = "Properties",
                    initialState = !Data.currentState,
                    onStateChanged = {
                        Data.currentState = it
                        Log.d("ManagerHomeScreen", "onStateChanged: $it")
//                        Data.getIssuesForManager()
                    },
                )
            }

            Column(horizontalAlignment = Alignment.Start,modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.Start)) {
                if (!Data.currentState) {
                    /*show issues overview*/
                    IssuesOverview(viewModel = Data, navigate = navigate)
//                    when (val allissues = Data.issuesForManagerResponse) {
//                        is Response.Success -> {
//                            if (allissues.data.isEmpty()) {
//                                Text(text = "No issues found")
//                            } else {
//                                IssueOverview(
//                                    issues = allissues.data,
//                                    onIssueClicked = {/*route to details*/ },
//                                    onStatusClicked = { status,issueid,propertyid -> Data.changeIssueStatus(propertyid,status,propertyid) })
//                            }
//                        }
//                        else -> {
//                            CircularProgressIndicator(backgroundColor = MainGroen,color = AccentLicht)
//                        }
//                    }

                } else {
                    /*show properties overview*/
                    PropertiesOverview(viewModel = Data, navigate = navigate)
//                    when (val allproperties = Data.ownedPropertiesResponseFormatted) {
//                        is Response.Success -> {
//                            if (allproperties.data.isEmpty()) {
//                                Text(text = "No properties found")
//                            } else {
//                                PropertyOverview(
//                                    properties = allproperties.data,
//                                    onPropertyClicked = {/*route to details*/ })
//                            }
//                        }
//                        else -> {
//                            CircularProgressIndicator(backgroundColor = MainGroen,color = AccentLicht)
//                        }
//                    }
                }
            }
            }
    },
        floatingActionButton = {if (Data.currentState) {addButton(contentDescription = "Add property", onClick = {navigate("${MyDestinations.ADD_PROPERTY}/newProperty")})}}

    )

}

@Composable
fun addButton(contentDescription: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        FloatingActionButton(
            backgroundColor = MainGroen,
            onClick = onClick,
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            Icon(imageVector = Icons.Rounded.Add, contentDescription = contentDescription, tint = AccentLicht)
        }
    }
}


@Preview
@Composable
fun ManagerHomeScreenPreview(){
    //ManagerHomeScreen()
}


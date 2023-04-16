package com.ugnet.sel1.ui.manager

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.ugnet.sel1.domain.models.Issue
import com.ugnet.sel1.domain.models.Pand
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.ui.components.*
import kotlinx.coroutines.launch

@Composable
fun ManagerHomeScreen(Data:ManagerHomeVM=hiltViewModel(), initialScreen:Boolean=false){

    //data
    val drawerItems = listOf(
        MenuItem(
            name = "Home",
            route = "managerhome",
            icon = Icons.Rounded.Home,
        ),
        MenuItem(
            name = "Profile",
            route = "profile",
            icon = Icons.Rounded.Person,
        ),
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
    Scaffold(scaffoldState = scaffoldState,
        topBar = {ResidentTopBar(
        onNavigationIconClick = {coroutineScope.launch {
            scaffoldState.drawerState.open()
        } },
        topBarTitle = currentTitle
    )}, drawerContent = {
            DrawerHeader()
            DrawerBody(items=drawerItems,onItemClick={})
        }, content={ padding ->
        Column(modifier = Modifier.padding(padding)) {
            SwitchButton2(
                option1 = "Issues",
                option2 = "Properties",
                initialState = initialScreen,
                onStateChanged = {
                    setCurrentState(it)
                }
            )
            if (currentState) {
                IssueDataContainer(viewModel = Data, IssueContent ={issues-> IssueOverview(
                    issues = issues,
                    onIssueClicked = {}
                )})
            } else {
                PropertyDataContainer(viewModel = Data, IssueContent ={properties-> PropertyOverview(
                    properties = properties,
                    onPropertyClicked = {}
                )})
            }
        }
    })

}

@Composable
fun IssueDataContainer(
    viewModel: ManagerHomeVM = hiltViewModel(),
    IssueContent: @Composable (Issues: List<IssueData> ) -> Unit
) {
    when(val issueResponse = viewModel.IssueResponse) {
        is Response.Loading -> Text(text = "Loading")
        is Response.Success -> IssueContent(processIssues(issueResponse.data))
        is Response.Failure -> print(issueResponse.e)
    }
}

fun processIssues(issueList: List<Issue>): List<IssueData> {
    //
    return null!!
}

fun processProperties(propertyList: List<Pand>): List<PropertyData> {
    //tranfer the data from the pand model to the property data model
    return null!!
}

@Composable
fun PropertyDataContainer(
    viewModel: ManagerHomeVM = hiltViewModel(),
    IssueContent: @Composable (Issues: List<PropertyData> ) -> Unit
) {
    when(val propertyResponse = viewModel.PropertyResponse) {
        is Response.Loading -> Text(text = "Loading")
        is Response.Success -> IssueContent(processProperties(propertyResponse.data))
        is Response.Failure -> print(propertyResponse.e)
    }
}

@Preview
@Composable
fun ManagerHomeScreenPreview(){
    ManagerHomeScreen()
}


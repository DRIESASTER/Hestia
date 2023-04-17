package com.ugnet.sel1.ui.manager

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.ugnet.sel1.domain.models.Issue
import com.ugnet.sel1.domain.models.Property
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.domain.models.Status
import com.ugnet.sel1.domain.useCases.nieuwUsecases.GetOwnedProperties
import com.ugnet.sel1.ui.components.*
import com.ugnet.sel1.ui.theme.MainGroen
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
            if (!currentState) {
                IssueDataContainer(viewModel = Data, IssueContent ={issues-> IssueOverview(
                    issues = issues,
                    onIssueClicked = {}
                )})
            } else {
                PropertyDataContainer(viewModel = Data, PropertyContent ={properties-> PropertyOverview(
                    properties = properties,
                    onPropertyClicked = {}
                )})
            }
        }
    },
        bottomBar = {if (!currentState) {addButton(contentDescription = "Add property", onClick = {/**fix routing*/})}}
    )

}

@Composable
fun IssueDataContainer(
    viewModel: ManagerHomeVM = hiltViewModel(),
    IssueContent: @Composable (Issues: List<IssueData> ) -> Unit
) {
    when(val issueResponse = viewModel.issuesForRoomResponse) {
        is Response.Loading -> Text(text = "Loading")
        is Response.Success -> IssueContent(processIssues(issueResponse.data))
        is Response.Failure -> print(issueResponse.e)
    }
}

//FIXME: add tenant and room if fixed
fun processIssues(issueList: List<Issue>): List<IssueData> {
    //transfer the data from the issue model to the issue data model
    val issueDataList = mutableListOf<IssueData>()
    for (issue in issueList) {
        val issueData = IssueData(
            id = if (issue.roomId != null) issue.roomId!! else "0",
            title = if (issue.titel != null) issue.titel!! else "No title",
            description = if (issue.beschrijving != null) issue.beschrijving!! else "No title",
            status = if (issue.status != null) issue.status!! else Status.notStarted,
            tenant = "",
            room = "",
        )
        issueDataList.add(issueData)
    }
    return issueDataList
}

fun processProperties(propertyList: List<Property>): List<PropertyData> {
    //tranfer the data from the pand model to the property data model
    val propertyDataList = mutableListOf<PropertyData>()
    for (property in propertyList) {
        val propertyData = PropertyData(
            name = if (property.straat != null) property.straat!! else "No title",
            address = if (property.straat != null) property.straat!! else "No title",
            issues = listOf(),
            tenants = listOf()
        )
        propertyDataList.add(propertyData)
    }
    return propertyDataList
}

@Composable
fun PropertyDataContainer(
    viewModel: ManagerHomeVM = hiltViewModel(),
    PropertyContent: @Composable (Issues: List<PropertyData> ) -> Unit
) {
    when(val propertyResponse = viewModel.ownedPropertiesResponse) {
        is Response.Loading -> Text(text = "Loading")
        is Response.Success -> PropertyContent(processProperties(propertyResponse.data))
        is Response.Failure -> print(propertyResponse.e)
    }
}

@Composable
fun addButton(contentDescription: String, onClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        FloatingActionButton(
            onClick = { onClick()},
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


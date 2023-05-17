package com.ugnet.sel1.ui.announcements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ugnet.sel1.domain.models.Announcement
import com.ugnet.sel1.navigation.MyDestinations
import com.ugnet.sel1.ui.components.DrawerBody
import com.ugnet.sel1.ui.components.DrawerHeader
import com.ugnet.sel1.ui.components.MenuItem
import com.ugnet.sel1.ui.components.ResidentTopBar
import com.ugnet.sel1.ui.manager.addButton
import com.ugnet.sel1.ui.theme.AccentLicht
import com.ugnet.sel1.ui.theme.MainGroen
import com.ugnet.sel1.ui.theme.RedProgress
import kotlinx.coroutines.launch

@Composable
fun AnnouncementScreen(viewModel: AnnouncementsViewModel, navigate: (String) -> Unit) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    var isPopupVisible by remember { mutableStateOf(false) }
    when (val uiState = viewModel.uiState.collectAsState().value) {
        AnnouncementUiState.Loading -> {
            CircularProgressIndicator()
        }
        is AnnouncementUiState.Success -> {
            val user = uiState.currentUser
            val announcements = uiState.announcements

            //hamburgermenu
            var homeroute = MyDestinations.MANAGER_HOME_ROUTE
            if (user.accountType=="Huurder"){
                homeroute = MyDestinations.HIREE_HOME_ROUTE
            }

            val drawerItems = listOf(
                MenuItem(
                    name = "Home",
                    route = homeroute,
                    icon = Icons.Rounded.Home,
                ),
                MenuItem(name= "Logout",
                    route = MyDestinations.ROLE_SELECTION_ROUTE,
                    icon = Icons.Rounded.ExitToApp),

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

            val drawerItemsRenter = listOf(
                MenuItem(
                    name = "Profile",
                    route = MyDestinations.HIREE_HOME_ROUTE,
                    icon = Icons.Rounded.Person,
                ),
                MenuItem(
                    name = "Issues",
                    route = "hiree_home/Issues",
                    icon = Icons.Rounded.Notifications,
                ),
                MenuItem(
                    name = "Logout",
                    route = MyDestinations.ROLE_SELECTION_ROUTE,
                    icon = Icons.Rounded.Logout,
                ),
                MenuItem(
                    name = "Announcements",
                    route = MyDestinations.ANNOUNCEMENT_ROUTE,
                    icon = Icons.Rounded.Announcement,
                ),
            )

            Scaffold(
                modifier = Modifier.fillMaxSize(1.0f),
                scaffoldState = scaffoldState,
                topBar = {
                    ResidentTopBar(
                        onNavigationIconClick = {
                            coroutineScope.launch {
                                scaffoldState.drawerState.open()
                            }
                        },
                        topBarTitle = "Announcements"
                    )
                },
                floatingActionButton = {
                    if (user.accountType == "Manager") {
                        addButton(contentDescription = "Add property", onClick = {
                            isPopupVisible = true
                        })
                    }
                },
                drawerContent = {
                    DrawerHeader()
                    if (user.accountType == "Manager") {
                        DrawerBody(items=drawerItems,onItemClick={
                                item ->
                            if (item.name == "Logout"){
                            viewModel.logout()
                            }
                            navigate(item.route)})
                    } else {
                        DrawerBody(items=drawerItemsRenter,onItemClick={item -> navigate(item.route)})
                    }
                },
                content = { paddingValues ->
                    if (announcements.isEmpty()) {
                        Text(text = "No announcements found")
                    } else {
                        AnnouncementOverview(
                            announcements = announcements
                        )
                    }
                    if(isPopupVisible) {
                        AlertDialog(
                            modifier= Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            onDismissRequest = { isPopupVisible = false },
                            text = { AnnouncementsAddScreen(onSave = { isPopupVisible = false }) }, buttons = {}
                        )
                    }
                }
            )
        }
    }
}

@Composable
fun AnnouncementOverview(modifier: Modifier = Modifier, announcements:List<Announcement>) {
    Surface(modifier = modifier) {
        LazyColumn (horizontalAlignment = Alignment.Start, modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.9f)){
            itemsIndexed(announcements.reversed()) { _, announcement ->
                Card(backgroundColor = MainGroen, modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .clip(
                        RoundedCornerShape(20.dp)
                    ),
                    elevation = 8.dp
                ) {
                    Column(modifier = Modifier
                        .clip(RoundedCornerShape(30.dp))
                        .padding(15.dp)
                        .background(AccentLicht, shape = RoundedCornerShape(20.dp))
                    ) {
                        Icon(imageVector = Icons.Rounded.Notifications, contentDescription = "alert",tint= RedProgress,modifier = Modifier
                            .padding(4.dp)
                            .size(30.dp))
                        Text(text = announcement.text!!,
                            fontSize = 16.sp,
                            color = Color.Black,
                            modifier = Modifier.padding(bottom = 10.dp, start = 15.dp, end = 15.dp))
                    }


                }

                Spacer(modifier = Modifier.height(0.dp))
            }
        }
    }
}


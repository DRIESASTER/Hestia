package com.ugnet.sel1.ui.announcements

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ugnet.sel1.common.snackbar.SnackbarManager
import com.ugnet.sel1.domain.models.Announcement
import com.ugnet.sel1.domain.models.Response
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

    //hamburgermenu
    val drawerItems = listOf(
        MenuItem(
            name = "Home",
            route = MyDestinations.MANAGER_HOME_ROUTE,
            icon = Icons.Rounded.Home,
        ),
        MenuItem(name= "Logout",
            route = "logout",
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

    when (val uiState = viewModel.uiState.collectAsState().value) {
        AnnouncementUiState.Loading -> {
            // Show loading state UI
        }
        is AnnouncementUiState.Success -> {
            val user = uiState.currentUser
            val announcements = uiState.announcements

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
                            navigate(MyDestinations.ANNOUNCEMENT_ADD_ROUTE)
                        })
                    }
                },
                drawerContent = {
                    DrawerHeader()
                    DrawerBody(items=drawerItems,onItemClick={item -> navigate(item.route)})
                },
                content = { paddingValues ->
                    if (announcements.isEmpty()) {
                        Text(text = "No announcements found")
                    } else {
                        AnnouncementOverview(
                            announcements = announcements
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
            itemsIndexed(announcements) { _, announcement ->
                Card(backgroundColor = MainGroen, modifier = Modifier.fillMaxWidth().padding(20.dp).clip(
                        RoundedCornerShape(20.dp)),
                    elevation = 8.dp
                    ) {
                    Column(modifier = Modifier.clip(RoundedCornerShape(30.dp)).padding(15.dp).background(AccentLicht, shape = RoundedCornerShape(20.dp))
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


package com.ugnet.sel1.ui.resident

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import com.ugnet.sel1.ui.components.DrawerBody
import com.ugnet.sel1.ui.components.DrawerHeader
import com.ugnet.sel1.ui.components.MenuItem
import com.ugnet.sel1.ui.components.ResidentTopBar
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ResidentHomeScreen() {
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

    var currentTitle = drawerItems[0].name
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
                    println("Clicked on ${it.name}") //TODO: add routing function
                    currentTitle = it.name //Does not work as intended
                }
            )
        },
        content = { Text(text = "test string") }
    )
}
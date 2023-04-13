package com.ugnet.sel1.ui.resident

import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.ugnet.sel1.ui.components.BottomNavItem

@Composable
fun ResidentHomeScreen() {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    val bottomNavItems = listOf(
        BottomNavItem(
            name = "Profile",
            route = "profile",
            icon = Icons.Rounded.Person,
        ),
        BottomNavItem(
            name = "Issues",
            route = "issues",
            icon = Icons.Rounded.Notifications,
        ),
        BottomNavItem(
            name = "Chat",
            route = "chat",
            icon = Icons.Rounded.Chat,
        ),
        BottomNavItem(
            name = "Events",
            route = "events",
            icon = Icons.Rounded.CalendarToday
        ),
        BottomNavItem(
            name = "Announcements",
            route = "announcements",
            icon = Icons.Rounded.Campaign
        )
    )

//    Scaffold(
//        scaffoldState = scaffoldState,
//        drawerContent = null,
//        content = null
//    )
}

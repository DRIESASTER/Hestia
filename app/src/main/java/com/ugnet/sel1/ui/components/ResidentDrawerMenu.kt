package com.ugnet.sel1.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.ui.res.painterResource
import com.ugnet.sel1.ui.theme.AccentLicht
import androidx.compose.ui.R

@Composable
fun ResidentDrawerMenu() {

    //TODO: Add all items of the drawer menu
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

    //TODO: figure out a way to give the current page to ResedentTopBar
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 8.dp, top = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .size(126.dp)
                .clip(CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            Icon(imageVector = Icons.Rounded.Home,
                contentDescription = "Drawer Icon",
                tint = AccentLicht,
                modifier = Modifier.size(128.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Preview
@Composable
fun ResidentDrawerMenuPreview() {
    ResidentDrawerMenu()
}
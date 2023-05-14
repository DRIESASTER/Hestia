package com.ugnet.sel1.ui.announcements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ugnet.sel1.navigation.MyDestinations
import com.ugnet.sel1.ui.components.ResidentTopBar
import com.ugnet.sel1.ui.manager.addButton
import kotlinx.coroutines.launch

@Composable
fun AnnouncementScreen(viewModel: AnnouncementsViewModel, navigate: (String) -> Unit) {

    val scaffoldState = rememberScaffoldState();
    val coroutineScope = rememberCoroutineScope()

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
                topBarTitle = ""
            )
        },
        floatingActionButton = {
            if (true) {
                addButton(contentDescription = "Add property", onClick = {
                    navigate(MyDestinations.ANNOUNCEMENT_ADD_ROUTE)
                })
            }
        },
        content = { paddingValues ->
            // Here you should put the main content of your screen
            // It can be a column, row, box, etc.
            // Example:
            Column(
                modifier = Modifier.padding(paddingValues)
            ) {
                Text("This is an announcement screen.")
            }
        },
    )
}


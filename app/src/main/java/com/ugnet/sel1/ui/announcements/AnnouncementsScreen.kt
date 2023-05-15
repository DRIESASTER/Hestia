package com.ugnet.sel1.ui.announcements

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ugnet.sel1.common.snackbar.SnackbarManager
import com.ugnet.sel1.domain.models.Announcement
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.navigation.MyDestinations
import com.ugnet.sel1.ui.components.ResidentTopBar
import com.ugnet.sel1.ui.manager.addButton
import kotlinx.coroutines.launch

@Composable
fun AnnouncementScreen(viewModel: AnnouncementsViewModel, navigate: (String) -> Unit) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

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
                        topBarTitle = ""
                    )
                },
                floatingActionButton = {
                    if (user.accountType == "Manager") {
                        addButton(contentDescription = "Add property", onClick = {
                            navigate(MyDestinations.ANNOUNCEMENT_ADD_ROUTE)
                        })
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
                }
            )
        }
    }
}

@Composable
fun AnnouncementOverview(modifier: Modifier = Modifier, announcements:List<Announcement>) {
    Surface(modifier = modifier) {
        LazyColumn (horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxWidth()){
            itemsIndexed(announcements) { _, announcement ->
                Text(
                    text = announcement.text!!,
                    modifier = Modifier.padding(8.dp),
                    style = MaterialTheme.typography.body1
                )
                Spacer(modifier = Modifier.height(0.dp))
            }
        }
    }
}


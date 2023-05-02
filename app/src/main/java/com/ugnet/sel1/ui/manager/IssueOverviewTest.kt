package com.ugnet.sel1.ui.manager

import android.util.Log
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.Timestamp
import com.ugnet.sel1.domain.models.*
import com.ugnet.sel1.ui.chat.ChatViewModel
import com.ugnet.sel1.ui.chat.components.ChatWindowDialog
import com.ugnet.sel1.ui.components.IssueCard
import com.ugnet.sel1.ui.theme.AccentLicht
import com.ugnet.sel1.ui.theme.MainGroen


@Composable
fun IssuesOverviewTest(viewModel: ManagerHomeVM) {
    // ...
    var isChatWindowVisible by remember { mutableStateOf(false) }
    var selectedIssue by remember { mutableStateOf<Issue?>(null) }

    when (val response = viewModel.ownedPropertiesResponse) {
        is Response.Success -> {
            if (response.data.isEmpty()) {
                Text(text = "No issues found")
            } else {
                IssueOverviewTest(
                    properties = response.data,
                    onIssueClicked = { issue ->
                        selectedIssue = issue
                        isChatWindowVisible = true
                    },
                    onStatusClicked = { status, issueId, propertyId -> viewModel.changeIssueStatus(issueId, status, propertyId) },
                    viewModel = viewModel
                )
            }
        }
        else -> {
            CircularProgressIndicator(backgroundColor = MainGroen, color = AccentLicht)
        }
    }

    if (isChatWindowVisible && selectedIssue != null) {
        ChatWindowDialog(selectedIssue!!) {
            isChatWindowVisible = false
        }
    }
}


@Composable
fun IssueOverviewTest(modifier: Modifier = Modifier, properties:List<Property>, onIssueClicked:(Issue)->Unit, onStatusClicked:(Status, String, String)->Unit, viewModel:ManagerHomeVM) {
    Log.d("IssueOverview", "IssueOverview loading properties: $properties")
    Surface(modifier = modifier) {
        LazyColumn (horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()){
            itemsIndexed(properties) { _, property ->
                viewModel.getIssuesPerProperty(property.propertyId!!).collectAsState(initial = Response.Loading).value.let {
                    when (it) {
                        is Response.Success -> {
                            Log.d("IssueOverview", "IssueOverview loading issues: ${it.data}")
                            if (it.data.isNotEmpty()) {

                                viewModel.getIssuesPerProperty(property.propertyId!!).collectAsState(
                                    initial = Response.Loading
                                )
                                for(issue in it.data) {
                                    viewModel.getUser(issue.userId!!).collectAsState(initial = Response.Loading).value.let { user ->
                                        when (user){
                                            is Response.Success -> {
                                                val username = user.data!!.voornaam + " " + user.data.achternaam
                                                Log.d("IssueOverview", "IssueOverview loading user: $username")
                                                IssueCard(
                                                    id = issue.issueId!!,
                                                    title = issue.titel!!,
                                                    tenant = username,
                                                    room = property.straat!!+" "+property.huisnummer!!+", "+property.postcode!!+" "+property.stad!!,
                                                    description = issue.beschrijving!!,
                                                    status = issue.status!!,
                                                    onClick = { onIssueClicked(issue) },
                                                    onStatusClicked = onStatusClicked,
                                                    propertyid = property.propertyId!!)
                                            }
                                            else -> {
                                                CircularProgressIndicator(backgroundColor = MainGroen,color = AccentLicht)
                                            }
                                        }

                                    }


                                }
                                Spacer(modifier = Modifier.height(5.dp))
                            }
                        }
                        else -> {
                            Spacer(modifier = Modifier.height(0.dp))
                        }}
                }
                Spacer(modifier = Modifier.height(0.dp))
            }
        }
    }
}



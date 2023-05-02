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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.Timestamp
import com.ugnet.sel1.domain.models.*
import com.ugnet.sel1.navigation.MyDestinations
import com.ugnet.sel1.ui.components.IssueCard
import com.ugnet.sel1.ui.theme.AccentLicht
import com.ugnet.sel1.ui.theme.MainGroen

@Composable
fun IssuesOverview(viewModel:ManagerHomeVM, navigate : (String) -> Unit) {
//    ownedPropertiesResponseFormatted = Response.Loading
    when(val response = viewModel.ownedPropertiesResponse){
        is Response.Success -> {
            if (response.data.isEmpty()) {
                Text(text = "No issues found")
            } else {
                IssueOverview(
                    properties = response.data,
                    onIssueClicked = { route -> navigate(route) },
                    onStatusClicked = {status,issueid,propertyid -> viewModel.changeIssueStatus(issueid,status,propertyid)},
                    viewModel = viewModel)
            }
        }
        else -> {
            CircularProgressIndicator(backgroundColor = MainGroen,color = AccentLicht)
        }
    }
}
@Composable
fun IssueOverview(modifier: Modifier = Modifier, properties:List<Property>, onIssueClicked : (String) -> Unit, onStatusClicked:(Status, String, String)->Unit, viewModel:ManagerHomeVM) {
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
                                            val route = MyDestinations.ISSUE_ROUTE.replace("{${MyDestinations.IssueArgs.IssueId}}", issue.issueId!!)
                                            Log.d("IssueOverview", "IssueOverview loading user: $username")
                                            IssueCard(
                                                id = issue.issueId!!,
                                                title = issue.titel!!,
                                                tenant = username,
                                                room = property.straat!!+" "+property.huisnummer!!+", "+property.postcode!!+" "+property.stad!!,
                                                description = issue.beschrijving!!,
                                                status = issue.status!!,
                                                onClick = { onIssueClicked(route) },
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





//@Preview
//@Composable
//fun IssueOverviewPreview() {
//    val mockIssueDataList = createMockIssueDataList()
//    IssueOverview(issues = mockIssueDataList, onIssueClicked = {/* Do nothing */}, onStatusClicked = { _, _, _ -> /* Do nothing */})
//}

fun createMockIssueDataList(): List<IssueData> {
    val mockIssueDataList = mutableListOf<IssueData>()

    for (i in 0..12) {
        val issueData = IssueData(
            id = i.toString(),
            title = "test${i}",
            description = "test${i}",
            status = Status.notStarted,
            tenant = "test${i}",
            room = "test${i}",
            issuekind = IssueType.electricity,
            date = Timestamp.now(),
            building = "test${i}"
        )
        mockIssueDataList.add(issueData)
    }

    return mockIssueDataList
}

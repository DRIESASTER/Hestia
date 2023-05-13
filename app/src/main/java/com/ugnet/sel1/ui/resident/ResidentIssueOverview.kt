package com.ugnet.sel1.ui.resident

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ugnet.sel1.ui.theme.MainGroen
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.ui.theme.AccentLicht
import androidx.compose.runtime.collectAsState
import com.ugnet.sel1.navigation.MyDestinations
import com.ugnet.sel1.ui.components.ResidentIssueCard
import com.ugnet.sel1.ui.manager.addButton

@Composable
fun ResidentIssueOverview(
    viewModel: ResidentHomeVM,
    modifier: Modifier = Modifier,
    navigate : (String) -> Unit,
    onIssueClicked : (String) -> Unit,
) {
    Surface(
        modifier = modifier,
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //Get all properties that the current user is renting
            when(val response = viewModel.allRentedPropertiesResponse) {
                is Response.Success -> {
                    if (response.data.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(1.0f)
                                .padding(8.dp)
                        ) {
                            Text(
                                text = "You are currently not renting any properties",
                                color = Color.Gray.copy(alpha = 0.5f),
                                fontSize = 24.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    } else {
                        LazyColumn(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxWidth()
                                //.weight(1f)
                        ) {
                            itemsIndexed(response.data) {_, property ->
                                Log.d("propertyLog", "Looping over properties")
                                viewModel.getAccesibleRoomsForProperty(property.propertyId!!).collectAsState(initial = Response.Loading).value.let { rooms ->
                                    when(rooms) {
                                        is Response.Success -> {
                                            for (room in rooms.data) {
                                                Log.d("roomLog", "Looping over rooms")
                                                viewModel.getIssuesForRoom(property.propertyId!!, room.roomId!!).collectAsState(initial = Response.Loading).value.let {
                                                    when(it) {
                                                        is Response.Success -> {
                                                            if (it.data.isNotEmpty()) {
                                                                for (issue in it.data) {
                                                                    //Get the user for an issue(for display purposes)
                                                                    Log.d("issueLog", "Looping over issues")
                                                                    viewModel.getUser(issue.userId!!).collectAsState(initial = Response.Loading).value.let { user ->
                                                                        when (user){
                                                                            is Response.Success -> {
                                                                                if(user.data == null) {
                                                                                    Log.d("userNull", "User you are getting is null")
                                                                                } else {
                                                                                    val username = user.data!!.voornaam + " " + user.data.achternaam
                                                                                    val route = MyDestinations.ISSUE_ROUTE
                                                                                        .replace("{${MyDestinations.IssueArgs.IssueId}}", issue.issueId!!)
                                                                                        .replace("{${MyDestinations.IssueArgs.PropId}}", property.propertyId!!)
                                                                                    Log.d("IssueOverview", "IssueOverview loading user: $username")
                                                                                    ResidentIssueCard(
                                                                                        title = issue.titel!!,
                                                                                        tenant = username,
                                                                                        room = property.straat!!+" "+property.huisnummer!!+", "+property.postcode!!+" "+property.stad!!,
                                                                                        description = issue.beschrijving!!,
                                                                                        status = issue.status!!,
                                                                                        onClick = { onIssueClicked(route) }
                                                                                    )
                                                                                }
                                                                            }
                                                                            else -> {
                                                                                CircularProgressIndicator(backgroundColor = MainGroen,color = AccentLicht)
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        else -> {
                                                            Spacer(modifier = Modifier.height(0.dp))
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        else -> {
                                            CircularProgressIndicator(backgroundColor = MainGroen,color = AccentLicht)
                                        }
                                    }
                                }
                            }
                        }
                        addButton(contentDescription = "addIssueButton",
                            modifier = Modifier.padding(15.dp),
                            onClick = {navigate(MyDestinations.ADD_ISSUE_ROUTE)})
                    }
                }
                else -> {
                    CircularProgressIndicator(backgroundColor = MainGroen,color = AccentLicht)
                }
            }
        }
    }
}

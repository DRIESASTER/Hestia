package com.ugnet.sel1.ui.resident

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ugnet.sel1.ui.manager.IssueData
import com.ugnet.sel1.ui.theme.MainGroen
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.ui.theme.AccentLicht
import androidx.compose.runtime.collectAsState
import com.ugnet.sel1.ui.components.IssueCard

@Composable
fun ResidentIssueOverview(
    viewModel: ResidentHomeVM,
    modifier: Modifier = Modifier,
    issues:List<IssueData>,
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
                                .weight(1f)
                        ) {
                            itemsIndexed(response.data) {_, property ->
                                //Get all issues for each room that user has access to in a given property
                                viewModel.getIssuesForRenter(property.propertyId!!).collectAsState(initial = Response.Loading).value.let {
                                    when(it) {
                                        is Response.Success -> {
                                            for (issue in it.data) {
                                                //Get the user for an issue(for display purposes)
                                                viewModel.getUser(issue.userId!!).collectAsState(initial = Response.Loading).value.let { user ->
                                                    when (user){
                                                        is Response.Success -> {
                                                            val username = user.data!!.voornaam + " " + user.data.achternaam
                                                            Log.d("IssueOverview", "IssueOverview loading user: $username")
                                                            ResidentIssueCard(
                                                                title = issue.titel!!,
                                                                tenant = username,
                                                                room = property.straat!!+" "+property.huisnummer!!+", "+property.postcode!!+" "+property.stad!!,
                                                                description = issue.beschrijving!!,
                                                                status = issue.status!!
                                                            )
                                                        }
                                                        else -> {
                                                            CircularProgressIndicator(backgroundColor = MainGroen,color = AccentLicht)
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
                    }
                }
                else -> {
                    CircularProgressIndicator(backgroundColor = MainGroen,color = AccentLicht)
                }
            }
            addIssueButton {}
        }
    }
}

@Composable
fun addIssueButton(onAddButtonClick:() -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth(1.0f)
            .padding(8.dp),
    ) {
        OutlinedButton(
            onClick = { onAddButtonClick },
            modifier = Modifier
                .size(50.dp)
                .align(Alignment.BottomCenter),
            shape = CircleShape,
            border = BorderStroke(5.dp, MainGroen),
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Blue)
        ) {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = "add issue button",
                tint = MainGroen
            )
        }
    }
}

@Preview
@Composable
fun ResidentIssueOverviewPreview() {
    //ResidentIssueOverview(issues = createMockIssueDataList())
}

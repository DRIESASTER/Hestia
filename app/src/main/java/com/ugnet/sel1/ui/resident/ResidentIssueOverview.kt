package com.ugnet.sel1.ui.resident

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

@Composable
fun ResidentIssueOverview(
    data: ResidentHomeVM,
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
            when(val response = data.allRentedPropertiesResponse) {
                is Response.Success -> {
                    //TODO: for each property -> load rented rooms -> for each room load all issues
                    if (response.data.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(1.0f)
                                //.fillMaxSize(1.0f) // it will fill parent box
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
                        IssueList()
                    }
                }
                else -> {
                    CircularProgressIndicator(backgroundColor = MainGroen,color = AccentLicht)
                }
            }
            if (issues.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(1.0f)
                        //.fillMaxSize(1.0f) // it will fill parent box
                        .padding(8.dp)
                ) {
                    Text(
                        text = "you currently have no issues",
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
                    itemsIndexed(issues) { _, issue ->
                        ResidentIssueCard(
                            title = issue.title,
                            tenant = issue.tenant,
                            room = issue.room,
                            description = issue.description,
                            status = issue.status
                        )
                    }
                }
            }
            addIssueButton {}
        }
    }
}

//TODO: place the actual issue list in this function for readability
@Composable
fun IssueList() {

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

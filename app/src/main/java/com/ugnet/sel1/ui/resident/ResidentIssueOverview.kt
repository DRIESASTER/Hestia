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
import com.ugnet.sel1.domain.models.Status
import com.ugnet.sel1.ui.manager.IssueData
import com.ugnet.sel1.ui.theme.MainGroen

@Composable
fun ResidentIssueOverview(
    modifier: Modifier = Modifier,
    issues:List<IssueData>,
) {
    Surface(
        modifier = modifier,

    ) {
        Column(
            modifier = modifier.fillMaxWidth().fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
                    modifier = Modifier.fillMaxWidth()
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
            Box(
                modifier = Modifier
                    .fillMaxWidth(1.0f)
                    .padding(8.dp),
            ) {
                OutlinedButton(
                    onClick = { /*TODO: make click add direct to add issue screen*/ },
                    modifier = Modifier.size(50.dp).align(Alignment.BottomCenter),
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
    }
}

@Preview
@Composable
fun ResidentIssueOverviewPreview() {
    ResidentIssueOverview(issues = emptyList())
}

/*
fun createMockIssueDataList(): List<IssueData> {
    val mockIssueDataList = mutableListOf<IssueData>()

    for (i in 0..12) {
        val issueData = IssueData(
            id = i.toString(),
            title = "test${i}",
            description = "test${i}",
            status = Status.notStarted,
            tenant = "test${i}",
            room = "test${i}"
        )
        mockIssueDataList.add(issueData)
    }

    return mockIssueDataList
}*/

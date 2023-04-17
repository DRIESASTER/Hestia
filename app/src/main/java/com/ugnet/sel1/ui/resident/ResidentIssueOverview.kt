package com.ugnet.sel1.ui.resident

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.ugnet.sel1.domain.models.Status
import com.ugnet.sel1.ui.manager.IssueData

@Composable
fun ResidentIssueOverview(
    modifier: Modifier = Modifier,
    issues:List<IssueData>,
) {
    Surface(modifier = modifier) {
        Row(
            modifier = modifier
        ) {
            if (issues.isEmpty()) {
                Text(
                    text = "you currently have no issues",
                    color = Color.Gray.copy(alpha = 0.5f),
                )
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
            //TODO: add addIssue button
        }
    }
}

@Preview
@Composable
fun ResidentIssueOverviewPreview() {
    ResidentIssueOverview(issues = createMockIssueDataList())
}

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
}
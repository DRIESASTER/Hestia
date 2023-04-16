package com.ugnet.sel1.ui.manager

import androidx.compose.material.Surface
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ugnet.sel1.domain.models.Status
import com.ugnet.sel1.ui.components.IssueCard

@Composable
fun IssueOverview(modifier: Modifier = Modifier,issues:List<IssueData>,onIssueClicked:(IssueData)->Unit) {
    Surface(modifier = modifier) {
        LazyColumn (horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()){
            itemsIndexed(issues) { _, issue ->
                IssueCard(
                    title = issue.title,
                    tenant = issue.tenant,
                    room = issue.room,
                    description = issue.description,
                    status = issue.status,
                    onClick = { onIssueClicked(issue) }
                )
                Spacer(modifier = Modifier.height(0.dp))
            }
        }
    }
}


@Preview
@Composable
fun IssueOverviewPreview() {
    val mockIssueDataList = createMockIssueDataList()
    IssueOverview(issues = mockIssueDataList, onIssueClicked = {/* Do nothing */})
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

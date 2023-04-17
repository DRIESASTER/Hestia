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
import com.google.firebase.Timestamp
import com.ugnet.sel1.domain.models.IssueType
import com.ugnet.sel1.domain.models.Status
import com.ugnet.sel1.ui.components.IssueCard

@Composable
fun IssueOverview(modifier: Modifier = Modifier,issues:List<IssueData>,onIssueClicked:(IssueData)->Unit,onStatusClicked:(Status,String,String)->Unit) {
    Surface(modifier = modifier) {
        LazyColumn (horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()){
            itemsIndexed(issues) { _, issue ->
                IssueCard(
                    id = issue.id,
                    building = issue.building,
                    title = issue.title,
                    tenant = issue.tenant,
                    room = issue.room,
                    description = issue.issuekind.toString(),
                    status = issue.status,
                    onClick = { onIssueClicked(issue) },
                    onStatusClicked =  onStatusClicked
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
    IssueOverview(issues = mockIssueDataList, onIssueClicked = {/* Do nothing */}, onStatusClicked = { _, _, _ -> /* Do nothing */})
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
            room = "test${i}",
            building = "test${i}",
            issuekind = IssueType.electricity,
            date = Timestamp.now()
        )
        mockIssueDataList.add(issueData)
    }

    return mockIssueDataList
}

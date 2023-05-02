package com.ugnet.sel1.ui.manager.issues

import androidx.compose.material.Text
import androidx.compose.runtime.Composable


@Composable
fun IssueDetailScreen(
    issueId : String,
    issueDetailVM: IssueDetailVM
){
    //Text(text = issueId)
    Text(text = issueDetailVM.issueTest)
}
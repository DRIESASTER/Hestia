package com.ugnet.sel1.ui.manager.issues

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ugnet.sel1.R
import com.ugnet.sel1.common.snackbar.SnackbarManager
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.navigation.MyDestinations


@Composable
fun IssueDetailScreen(
    viewModel: IssueDetailVM
){
    //Text(text = issueId)
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = viewModel.issueTest)
        Text(text = viewModel.propTest)
    }

    when (viewModel.issueDataResponse) {
        is Response.Loading -> {
            // No action required
        }
        is Response.Success -> {
            val issue = (viewModel.issueDataResponse as Response.Success).data
            Log.d("DETAIL RESPONSE", viewModel.issueDataResponse.toString())

        }
        is Response.Failure -> {
            SnackbarManager.showMessage(R.string.generic_error);
            //navigate(MyDestinations.ROLE_SELECTION_ROUTE)
        }
    }
}

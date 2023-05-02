package com.ugnet.sel1.ui.chat

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ugnet.sel1.domain.models.Issue
import com.ugnet.sel1.ui.chat.components.ChatMessage



@Composable
fun ChatScreen(
    chatViewModel: ChatViewModel = hiltViewModel(),
    currentUserEmail: String,
    issue : Issue,
    onDismiss: () -> Unit
) {
    val messages by chatViewModel.messages.collectAsState()

    Column {
        LazyColumn(modifier = Modifier.weight(1f)) {

        }

        //SendMessage(issueId = "yourIssueId", currentUserEmail = currentUserEmail, chatViewModel::sendMessage)
        //Text(text = chatViewModel._issue.value!!.issueId.toString())
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Button(onClick = onDismiss) {
                Text("Close")
            }
        }
    }
}
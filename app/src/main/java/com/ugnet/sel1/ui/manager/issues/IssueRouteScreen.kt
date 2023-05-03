package com.ugnet.sel1.ui.manager.issues

import android.graphics.Bitmap
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.map
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ugnet.sel1.domain.models.Issue
import com.ugnet.sel1.domain.models.Message
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.ui.chat.components.ChatMessage
import com.ugnet.sel1.ui.chat.components.ChatWindowDialog

@Composable
fun IssueDetailsScreen(
    issue: Issue,
    openChatWindow: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = issue.titel ?: "",
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = issue.beschrijving ?: "",
            style = MaterialTheme.typography.body1
        )

        Spacer(modifier = Modifier.height(8.dp))

/*        Image(
            bitmap = issue.image.asImageBitmap(),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Crop
        )*/

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = issue.status?.name ?: "",
            style = MaterialTheme.typography.body2,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = openChatWindow) {
            Text(text = "Open Chat")
        }
    }
}

@Composable
fun IssueRouteScreen(viewModel: IssueDetailVM) {

    val messages by viewModel.messages.map {
        when (it) {
            is Response.Success -> it.data
            else -> listOf()
        }
    }.observeAsState(listOf())

    var isChatWindowOpen by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        when (viewModel.issueDataResponse) {
            is Response.Loading -> CircularProgressIndicator(Modifier.align(Alignment.Center))
            is Response.Failure -> Text(text = "failed", Modifier.align(Alignment.Center))
            is Response.Success -> {
                val issueData = (viewModel.issueDataResponse as Response.Success).data
                IssueDetailsScreen(issueData!!) {
                    isChatWindowOpen = true
                }
                if (isChatWindowOpen) {
                    ChatWindowDialog(
                        issue = issueData,
                        messages = messages,
                        currentUserEmail = Firebase.auth.currentUser!!.email!!,
                        onDismiss = { isChatWindowOpen = false },
                        onSendMessage = { messageText ->
                            viewModel.sendMessage(messageText = messageText)
                        })
                }
            }
        }

    }
}













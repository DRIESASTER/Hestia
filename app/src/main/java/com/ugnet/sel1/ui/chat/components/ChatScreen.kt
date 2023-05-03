package com.ugnet.sel1.ui.chat.components

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ugnet.sel1.domain.models.Issue
import com.ugnet.sel1.domain.models.Message

@Composable
fun ChatScreen(
    currentUserEmail: String,
    issue: Issue,
    messages: List<Message>,
    onDismiss: () -> Unit,
    onSendMessage: (String) -> Unit
) {
    var messageInput by remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(text = "Chat") },
            navigationIcon = {
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            }
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp),
            reverseLayout = false
        ) {
            items(messages) { message ->
                ChatMessage(
                    message = message,
                    isCurrentUser = message.senderEmail == currentUserEmail
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = messageInput,
                onValueChange = { messageInput = it },
                modifier = Modifier.weight(1f),
                label = { Text("Type a message") },
                singleLine = true,
                maxLines = 1
            )

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(onClick = {
                if (messageInput.isNotEmpty()) {
                    onSendMessage(messageInput)
                    messageInput = ""
                }
            }) {
                Icon(Icons.Default.Send, contentDescription = "Send message")
            }
        }
    }
}

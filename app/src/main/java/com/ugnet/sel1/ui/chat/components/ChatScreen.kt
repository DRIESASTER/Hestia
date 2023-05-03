package com.ugnet.sel1.ui.chat.components

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ugnet.sel1.domain.models.Issue
import com.ugnet.sel1.domain.models.Message
import com.ugnet.sel1.domain.models.Status
import com.ugnet.sel1.ui.theme.AccentLicht
import com.ugnet.sel1.ui.theme.MainGroen

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
            title = { Text(text = "Chat", color = Color.White) },
            navigationIcon = {
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                }
            }, backgroundColor = MainGroen
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
                .fillMaxWidth(),
            state = rememberLazyListState(),
            reverseLayout = true
        ) {
            items(messages.reversed()) { message ->
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
                maxLines = 1,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = AccentLicht,
                    focusedIndicatorColor = MainGroen,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(onClick = {
                if (messageInput.isNotEmpty()) {
                    onSendMessage(messageInput)
                    messageInput = ""
                }
            }) {
                Icon(Icons.Default.Send, contentDescription = "Send message", tint = MainGroen)
            }
        }
    }
}

@Preview
@Composable
fun ChatScreenPreview() {
    ChatScreen(
        currentUserEmail = "milanhuurder@gmail.com",
issue = Issue(
            issueId = "1",
            titel = "Broken light",
            beschrijving = "The light in the kitchen is broken",
            roomId = "Kitchen",
            status = Status.notStarted,
            userId = "milanhuurder@gmail.com"), messages = listOf(Message()),{},{})
}
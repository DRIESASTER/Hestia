package com.ugnet.sel1.ui.chat.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.ugnet.sel1.domain.models.Issue
import com.ugnet.sel1.domain.models.Message



@Composable
fun ChatWindowDialog(
    issue: Issue,
    messages: List<Message>,
    currentUserEmail: String,
    onDismiss: () -> Unit,
    onSendMessage: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.4f))
            .clickable(onClick = onDismiss)
    ) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            elevation = 0.dp,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
                .align(Alignment.BottomCenter)
        ) {
            ChatScreen(
                currentUserEmail = currentUserEmail,
                issue = issue,
                messages = messages,
                onDismiss = onDismiss,
                onSendMessage = onSendMessage
            )
        }
    }
}
package com.ugnet.sel1.ui.chat.components

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.ugnet.sel1.domain.models.Issue
import com.ugnet.sel1.ui.chat.ChatScreen
import com.ugnet.sel1.ui.chat.ChatViewModel

@Composable
fun ChatWindowDialog(
    issue: Issue,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            elevation = 8.dp,
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .fillMaxHeight(0.8f)
        ) {
            ChatScreen(
                currentUserEmail = "yourUserEmail@example.com",
                issue = issue,
                onDismiss = onDismiss
            )
        }
    }
}
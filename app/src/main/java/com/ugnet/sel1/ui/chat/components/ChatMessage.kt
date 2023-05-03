package com.ugnet.sel1.ui.chat.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.Timestamp
import com.ugnet.sel1.domain.models.Message
import com.ugnet.sel1.ui.theme.AccentLicht
import com.ugnet.sel1.ui.theme.MainGroen

@Composable
fun ChatMessage(message: Message, isCurrentUser: Boolean) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Surface(
            modifier = Modifier
                .align(if (isCurrentUser) Alignment.CenterEnd else Alignment.CenterStart),
            color = if (isCurrentUser) MainGroen else AccentLicht,
            shape = RoundedCornerShape(8.dp),
            elevation = 4.dp
        ) {
            Text(
                text = message.messageText,
                modifier = Modifier.padding(8.dp),
                color = if (isCurrentUser) Color.White else Color.DarkGray
            )
        }
    }
}

@Preview
@Composable
fun ChatMessagePreview() {
    ChatMessage(
        message = Message(
            messageText = "Hello",
            timestamp = Timestamp.now()
        ),
        isCurrentUser = true
    )
    ChatMessage(message = Message(messageText = "HELP", timestamp = Timestamp.now()), isCurrentUser = false)
}
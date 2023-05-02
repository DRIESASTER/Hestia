package com.ugnet.sel1.ui.chat.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ugnet.sel1.domain.models.Message

@Composable
fun ChatMessage(message: Message, isCurrentUser: Boolean) {
    Row(
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalArrangement = if (isCurrentUser) Arrangement.End else Arrangement.Start
    ) {
        Surface(
            color = if (isCurrentUser) MaterialTheme.colors.primary else MaterialTheme.colors.surface,
            shape = RoundedCornerShape(8.dp),
            elevation = 1.dp
        ) {
            Text(
                text = message.messageText,
                modifier = Modifier.padding(8.dp),
                color = if (isCurrentUser) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSurface
            )
        }
    }
}
package com.ugnet.sel1.domain.models

import com.google.firebase.Timestamp


data class Message(
    val senderEmail: String = "",
    val messageText: String= "",
    val timestamp: Timestamp = Timestamp.now()
)


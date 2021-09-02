package com.app.sms.models

import androidx.annotation.Keep

@Keep
data class MessageGroup(
    val date: String,
    val messages: List<MessageItem>
)

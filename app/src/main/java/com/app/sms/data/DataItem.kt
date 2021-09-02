package com.app.sms.data

import com.app.sms.models.MessageItem

sealed class DataItem {

    abstract val id: String

    data class ItemDate(val date: String) : DataItem() {
        override val id = date
    }

    data class ItemMessage(val message: MessageItem) : DataItem() {
        override val id = message.id.toString()
    }

}
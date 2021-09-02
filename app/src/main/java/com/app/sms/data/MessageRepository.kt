package com.app.sms.data

import android.content.Context
import android.provider.Telephony
import android.text.format.DateUtils
import com.app.sms.models.MessageGroup
import com.app.sms.models.MessageItem
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import javax.inject.Inject


class MessageRepository @Inject constructor(
    @ApplicationContext private val ct: Context) {

    fun getMessages(limit: Int, offset: Int): ArrayList<DataItem> {

        Timber.d("LOAD >>>>>")

        val messages = ArrayList<MessageItem>()

        val cursor = ct.contentResolver.query(
            Telephony.Sms.CONTENT_URI,
            arrayOf(
                Telephony.Sms.Inbox._ID,
                Telephony.Sms.Inbox.ADDRESS,
                Telephony.Sms.Inbox.BODY,
                Telephony.Sms.Inbox.DATE
            ),
            null,
            null,
            Telephony.Sms.Inbox.DEFAULT_SORT_ORDER + " LIMIT " + limit + " OFFSET " + offset
        )
        if (cursor != null) {
            cursor.moveToFirst()
            while (!cursor.isAfterLast) {
                val id = cursor.getString(cursor.getColumnIndex(Telephony.Sms._ID))
                val number = cursor.getString(cursor.getColumnIndex(Telephony.Sms.ADDRESS))
                val body = cursor.getString(cursor.getColumnIndex(Telephony.Sms.BODY))
                val time = cursor.getLong(cursor.getColumnIndex(Telephony.Sms.DATE))

                messages.add(MessageItem(id, number, body, time))

                cursor.moveToNext()
            }
            cursor.close()
        }

        return groupMessages(messages)
    }

    private fun groupMessages(list: List<MessageItem>): ArrayList<DataItem> {

        val listItem = ArrayList<DataItem>()

        list.groupBy {
            DateUtils.getRelativeTimeSpanString(
                it.time ?: 0,
                System.currentTimeMillis(),
                DateUtils.HOUR_IN_MILLIS
            )
        }.map {
            MessageGroup(it.key.toString(), it.value)
        }.also {

            listItem.clear()

            it.forEach { group ->
                listItem.add(DataItem.ItemDate(group.date))
                group.messages.forEach { item ->
                    listItem.add(DataItem.ItemMessage(item))
                }
            }
        }
        listItem.distinct()
        return listItem
    }

}

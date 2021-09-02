package com.app.sms.models

import android.os.Parcelable
import androidx.annotation.Keep
import com.app.sms.utils.getFirst
import com.app.sms.utils.getFormattedTime
import kotlinx.android.parcel.Parcelize
import java.util.*


@Keep
@Parcelize
data class MessageItem(
    val id: String?,
    val number: String?,
    val body: String?,
    val time: Long?,
): Parcelable {
    val first = number.getFirst()
    val formattedTime = time.getFormattedTime()
}
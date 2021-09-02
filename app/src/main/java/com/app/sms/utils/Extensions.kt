package com.app.sms.utils

import android.content.Context
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

fun String?.getFirst(): String {
    return this?.let {
        if (this.isNotEmpty())
            this.first().toString()
        else ""
    } ?: kotlin.run { "" }
}

fun Long?.getFormattedTime(): String {
    return this?.let {
       return try {
           val date = Date(this)
           val format = SimpleDateFormat("dd MMM, hh:mm a")
            format.format(date)
       }catch (e:Exception){
           ""
       }
    } ?: kotlin.run { "" }
}

fun Context.showToast(msg:String){
    Toast.makeText(this,msg, Toast.LENGTH_SHORT).show()
}
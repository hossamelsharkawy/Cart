package com.hossamelsharkawy.base.extension

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Hossam Elsharkawy
0201099197556
on 11/6/18.  time :16:09

 */
var f: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
var dateTime: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:MM", Locale.ENGLISH)

val timePattern  = "mm-ss"
fun Date.format() = f.format(this)!!

fun Date.format( pattern :String) = SimpleDateFormat(pattern, Locale.ENGLISH).format(this)!!

fun Long.formatTime( ) = SimpleDateFormat(timePattern, Locale.ENGLISH).format(Date(this))!!


val fTime  by lazy {
    SimpleDateFormat("h:mm a", Locale.ENGLISH)
}



fun String.toDate() = f.parse(this)
fun String.toDateTime() = dateTime.parse(this)

fun String.toTime() = fTime.parse(this)


fun Long?.toCalendar() = this?.let { Calendar.getInstance().apply { timeInMillis = it } }


fun Calendar?.toMaxCalendar(dayNumber: Int) =
    this?.let {
        Calendar.getInstance().apply {
            timeInMillis = this@toMaxCalendar.timeInMillis
            add(Calendar.DATE, dayNumber)
           //add(Calendar.MINUTE, 5)
        }
    }
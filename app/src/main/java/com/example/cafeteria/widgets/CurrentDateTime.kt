package com.evapharma.cafeteriaapp.widgets

import android.icu.util.LocaleData
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.LocalDateTime.now
import java.util.*

fun getCurrentDateAndTime():String{
    val sdf = SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z")
    return sdf.format(Date())
}
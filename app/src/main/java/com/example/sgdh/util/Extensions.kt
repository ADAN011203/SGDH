package com.example.sgdh.util

import java.text.SimpleDateFormat
import java.util.*

fun String.toFormattedDate(): String {
    return try {
        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        parser.timeZone = TimeZone.getTimeZone("UTC")
        val date = parser.parse(this)

        val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        date?.let { formatter.format(it) } ?: this
    } catch (e: Exception) {
        this
    }
}

fun String.toShortDate(): String {
    return try {
        val parser = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = parser.parse(this)

        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        date?.let { formatter.format(it) } ?: this
    } catch (e: Exception) {
        this
    }
}
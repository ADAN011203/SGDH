package com.example.sgdh.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtils {

    fun formatDate(dateString: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            val date: Date = inputFormat.parse(dateString) ?: return dateString
            outputFormat.format(date)
        } catch (e: Exception) {
            dateString
        }
    }

    fun formatDateShort(dateString: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val date: Date = inputFormat.parse(dateString) ?: return dateString
            outputFormat.format(date)
        } catch (e: Exception) {
            dateString
        }
    }

    fun getCurrentDateTime(): String {
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        return format.format(Date())
    }
}
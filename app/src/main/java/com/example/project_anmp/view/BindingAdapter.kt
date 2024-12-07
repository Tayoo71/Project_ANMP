package com.example.project_anmp.view

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


// Fungsi untuk memformat datetime menjadi format 05\nSEP
@BindingAdapter("formattedDatetime")
fun bindFormattedDatetime(textView: TextView, datetime: String?) {
    if (datetime != null) {
        try {
            val parsedDateTime = LocalDateTime.parse(datetime, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            val dayFormatter = DateTimeFormatter.ofPattern("dd")
            val monthFormatter = DateTimeFormatter.ofPattern("MMM").withLocale(java.util.Locale.ENGLISH)

            val day = parsedDateTime.format(dayFormatter)
            val month = parsedDateTime.format(monthFormatter).uppercase()

            textView.text = "$day\n$month"
        } catch (e: Exception) {
            textView.text = "Invalid Date"
        }
    }
}
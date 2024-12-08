package com.example.project_anmp.view

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.project_anmp.model.ScheduleData
import com.squareup.picasso.Picasso
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

@BindingAdapter("android:imageUrl")
fun loadPhotoUrl(imageView: ImageView, url:String?){
    val picasso = Picasso.Builder(imageView.context)
    picasso.listener{picasso, uri, exception ->
        exception.printStackTrace()
    }
    picasso.build().load(url).into(imageView,
        object:com.squareup.picasso.Callback {
            override fun onSuccess() {
                imageView.visibility = View.VISIBLE
            }

            override fun onError(e: java.lang.Exception?) {
                imageView.visibility = View.INVISIBLE
                Log.e("Picasso", "Error loading image: ${e?.message}")
            }

        })
}

@BindingAdapter("formatLocScheduleDetail")
fun formatLocScheduleDetail(textView: TextView, schedule: ScheduleData?) {
    schedule?.let {
        try {
            // Parsing datetime from string to LocalDateTime
            val parsedDateTime = LocalDateTime.parse(it.datetime, DateTimeFormatter.ISO_LOCAL_DATE_TIME)

            // Create formatter for 12-hour time format (AM/PM)
            val timeFormatter = DateTimeFormatter.ofPattern("hh:mm a")

            // Format time
            val time = parsedDateTime.format(timeFormatter)

            // Set the formatted text to the TextView
            textView.text = "${it.location} ($time)"
        } catch (e: Exception) {
            // Handle invalid datetime or location
            textView.text = "Invalid Date/Location"
        }
    }
}
